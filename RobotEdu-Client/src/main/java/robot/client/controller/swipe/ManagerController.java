package robot.client.controller.swipe;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import robot.client.api.card.CardApi;
import robot.client.api.swipe.SwipeCardApi;
import robot.client.common.App;
import robot.client.common.Config;
import robot.client.common.DataOp;
import robot.client.dao.CardInfoDao;
import robot.client.dao.CustomerDao;
import robot.client.dao.SwipeCardRecordsDao;
import robot.client.db.DbHelper;
import robot.client.model.card.DataEduCardInfo;
import robot.client.model.card.EduCardInfo;
import robot.client.model.customer.EduCustomerInfo;
import robot.client.model.swipe.DataEduSwipeCardRecords;
import robot.client.model.swipe.EduSwipeCardRecords;
import robot.client.observer.Subject;
import robot.client.util.Logger;
import robot.client.util.PropertiesUtils;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Feng on 2017/5/18.
 */
public class ManagerController extends Subject implements Initializable {

    @FXML
    private TableView tableViewSwipe;
    @FXML
    private TextField txtSwipeNo;
    @FXML
    private TextField txtSelectCardNo;

    private ObservableList<DataEduSwipeCardRecords> dataSource = FXCollections.observableArrayList();


    public ManagerController() {
        this.attach(new SwipeCardApi());
        this.attach(new CardApi());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        this.loadDatas(null);
    }

    private void loadDatas(String cardNo) {
        dataSource.clear();

        // select datas from db
        LinkedList<DataEduSwipeCardRecords> dataEduSwipeCardRecords = SwipeCardRecordsDao.selectDatas(cardNo);

        for (DataEduSwipeCardRecords info : dataEduSwipeCardRecords) {
            dataSource.add(info);
        }

        ObservableList<TableColumn> observableList = tableViewSwipe.getColumns();

        observableList.get(0).setCellValueFactory(new PropertyValueFactory("cardNo"));
        observableList.get(1).setCellValueFactory(new PropertyValueFactory("childName"));
        observableList.get(2).setCellValueFactory(new PropertyValueFactory("createTime"));
//        observableList.get(3).setCellValueFactory(new PropertyValueFactory("createTime"));

        tableViewSwipe.setItems(dataSource);
    }

    @FXML
    private void onCardTextFieldKeyEnter(KeyEvent keyEvent) {
        if (KeyCode.ENTER.equals(keyEvent.getCode())) {
            // key enter
            Logger.debug("enter key pressed");
            if (StringUtils.isEmpty(txtSwipeNo.getText())) {
                return;
            }

            String cardNo = txtSwipeNo.getText();
            // select customer info
            EduCustomerInfo customerInfo = CustomerDao.select(cardNo);

            if (customerInfo != null) {
                // 查询上次刷卡时间，< 10分钟时，不予刷卡
                EduSwipeCardRecords lastSwipe = SwipeCardRecordsDao.selectLastSwipe(cardNo);
                if (lastSwipe != null) {
                    long times = System.currentTimeMillis() - lastSwipe.getCreateTime();
                    if (times < 1000 * 60 * 10) {
                        Alert dialog = new Alert(Alert.AlertType.WARNING);
                        dialog.setContentText("已经刷卡，请确认是否重复操作。");
                        dialog.showAndWait();
                        return;
                    }
                }

                // 查询卡信息
                EduCardInfo cardInfo = CardInfoDao.selectCardData(cardNo);
                if (cardInfo == null) {
                    // 无此卡信息记录
                    Alert dialog = new Alert(Alert.AlertType.WARNING);
                    dialog.setContentText("无次卡信息，请联系店员处理。");
                    dialog.showAndWait();
                    return;
                }

                if (cardInfo.getUsedTimes() >= cardInfo.getTotalTimes()) {
                    // 次数已用完
                    Alert dialog = new Alert(Alert.AlertType.WARNING);
                    dialog.setContentText("次数已用完，请联系店员处理。");
                    dialog.showAndWait();
                    return;
                }

                // 正常刷卡
                EduSwipeCardRecords swipeCardRecords = new EduSwipeCardRecords();
                swipeCardRecords.setCardNo(txtSwipeNo.getText());
                swipeCardRecords.setCreateTime(System.currentTimeMillis());
                if (StringUtils.isNotEmpty(customerInfo.getChildName())) {
                    swipeCardRecords.setChildName(customerInfo.getChildName());
                }
                this.insert(swipeCardRecords, cardInfo);
                this.loadDatas(null);

            } else {
                Alert dialog = new Alert(Alert.AlertType.WARNING);
                dialog.setContentText("无此卡信息，请联系店员处理。");
                dialog.showAndWait();
            }
            this.txtSwipeNo.setText("");
        }
    }


    /**
     * 保存数据到数据库
     */
    private void insert(EduSwipeCardRecords swipeCardRecords, EduCardInfo cardInfo) {

        try {
            // 同步处理
            synchronized (DbHelper.LOCK) {
                // 取消自动提交
                DbHelper.setAutoCommit(false);

                // 保存刷卡记录
                Integer ret = SwipeCardRecordsDao.insert(swipeCardRecords);
                if (ret > 0) {
                    // upload 刷卡记录
                    swipeCardRecords.setId(ret);
                    this.nodifyObservers(swipeCardRecords, DataOp.INSERT);
                }

                // 修改卡信息
                cardInfo.setUsedTimes(cardInfo.getUsedTimes() + 1);
                ret = CardInfoDao.swipeCard(cardInfo);
                if (ret > 0) {
                    // upload 改卡信息
                    this.nodifyObservers(cardInfo, DataOp.MODIFY);
                }
                // 提交数据更改
                DbHelper.commit();

                // 恢复自动提交
                DbHelper.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            Logger.error(ex.getMessage(), ex);
            try {
                DbHelper.rollback();
            } catch (SQLException e) {
                Logger.error(ex.getMessage(), ex);
            }
        }
    }

}

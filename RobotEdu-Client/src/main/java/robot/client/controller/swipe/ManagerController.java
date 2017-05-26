package robot.client.controller.swipe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.apache.commons.lang3.StringUtils;
import robot.client.api.swipe.SwipeCardApi;
import robot.client.common.DataOp;
import robot.client.dao.CustomerDao;
import robot.client.dao.SwipeCardRecordsDao;
import robot.client.model.customer.EduCustomerInfo;
import robot.client.model.swipe.DataEduSwipeCardRecords;
import robot.client.model.swipe.EduSwipeCardRecords;
import robot.client.observer.Subject;
import robot.client.util.Logger;

import java.net.URL;
import java.util.LinkedList;
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

            Long no = Long.valueOf(txtSwipeNo.getText());

            // select customer info
            EduCustomerInfo customerInfo = CustomerDao.select(no);

            EduSwipeCardRecords swipeCardRecords = new EduSwipeCardRecords();
            swipeCardRecords.setCardNo(Long.valueOf(txtSwipeNo.getText()));
            swipeCardRecords.setCreateTime(System.currentTimeMillis());
            swipeCardRecords.setChildName(customerInfo.getChildName());
            this.insert(swipeCardRecords);
            this.loadDatas(null);
            this.txtSwipeNo.setText("");
        }
    }


    /**
     * 保存数据到数据库
     */
    private EduSwipeCardRecords insert(EduSwipeCardRecords swipeCardRecords) {
        Integer ret = SwipeCardRecordsDao.insert(swipeCardRecords);
        if (ret > 0) {
            // update card info

            // upload
            swipeCardRecords.setId(ret.longValue());
            this.nodifyObservers(swipeCardRecords, DataOp.INSERT);
        }
        return swipeCardRecords;
    }

}

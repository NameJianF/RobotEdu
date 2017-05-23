package robot.client.controller.card;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import robot.client.api.card.CardApi;
import robot.client.dao.CardInfoDao;
import robot.client.db.DbHelper;
import robot.client.model.card.DataEduCardInfo;
import robot.client.model.card.EduCardInfo;
import robot.client.observer.Subject;

import java.util.LinkedList;

/**
 * Created by Feng on 2017/5/18.
 */
public class ManagerController extends Subject {

    @FXML
    private TableView cardInfoTableView;

    private ObservableList<DataEduCardInfo> dataSource = FXCollections.observableArrayList();

    public ManagerController() {
        this.attach(new CardApi());
//        this.loadDatas();
    }

    private void loadDatas() {

        // select datas from db
        LinkedList<DataEduCardInfo> dataEduCardInfos = CardInfoDao.selectDatas(null);

        for (DataEduCardInfo info : dataEduCardInfos) {
            dataSource.add(info);
        }

        ObservableList<TableColumn> observableList = cardInfoTableView.getColumns();

        observableList.get(0).setCellValueFactory(new PropertyValueFactory("id"));
        observableList.get(1).setCellValueFactory(new PropertyValueFactory("cardNo"));
        observableList.get(2).setCellValueFactory(new PropertyValueFactory("cardType"));
        observableList.get(3).setCellValueFactory(new PropertyValueFactory("totalTimes"));
        observableList.get(4).setCellValueFactory(new PropertyValueFactory("usedTimes"));
        observableList.get(5).setCellValueFactory(new PropertyValueFactory("price"));
        observableList.get(6).setCellValueFactory(new PropertyValueFactory("discount"));
        observableList.get(7).setCellValueFactory(new PropertyValueFactory("adviser"));
        observableList.get(8).setCellValueFactory(new PropertyValueFactory("flag"));
        observableList.get(9).setCellValueFactory(new PropertyValueFactory("createTime"));
        observableList.get(10).setCellValueFactory(new PropertyValueFactory("updateTime"));


        cardInfoTableView.setItems(dataSource);
    }

    /**
     * 保存数据到数据库
     */
    private void insert() {
        EduCardInfo cardInfo = new EduCardInfo();
        this.nodifyObservers(cardInfo);
    }
}

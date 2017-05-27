package robot.client.controller.card;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import robot.client.common.App;
import robot.client.common.PageInfo;
import robot.client.dao.CardInfoDao;
import robot.client.model.card.DataEduCardInfo;
import robot.client.util.PageUtil;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by Feng on 2017/5/18.
 */
public class ManagerController implements Initializable {

    @FXML
    private TableView cardInfoTableView;
    @FXML
    private TextField txtCardNo;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnModity;
    @FXML
    private Button btnDelete;

    private ObservableList<DataEduCardInfo> dataSource = FXCollections.observableArrayList();

    public ManagerController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadDatas(null);
        this.cardInfoTableView.setRowFactory(tv -> {
            TableRow<DataEduCardInfo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    DataEduCardInfo rowData = row.getItem();
                    tableViewRowDoubleClick(rowData);
                }
            });
            return row;
        });
    }

    private void loadDatas(Long cardNo) {
        dataSource.clear();

        // select datas from db
        LinkedList<DataEduCardInfo> dataEduCardInfos = CardInfoDao.selectDatas(cardNo);

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


    @FXML
    private void buttonSelectClick(MouseEvent event) {
        if (StringUtils.isEmpty(txtCardNo.getText())) {
            loadDatas(null);
        } else {
            loadDatas(Long.valueOf(txtCardNo.getText()));
        }
    }

    @FXML
    private void buttonNewClick(MouseEvent event) {
        Stage stage = new Stage();
        GridPane dialog = (GridPane) PageUtil.getPageInfo("/ui/card/edit.fxml").getNode();
        stage.setScene(new Scene(dialog));
        stage.setTitle("添加会员卡");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.primaryStage.getScene().getWindow());

        stage.getIcons().add(PageUtil.getLogo());
        stage.showAndWait();

        this.loadDatas(null);
    }

    @FXML
    private void buttonModifyClick(MouseEvent event) {
        DataEduCardInfo rowData = (DataEduCardInfo) this.cardInfoTableView.getSelectionModel().getSelectedItem();
        tableViewRowDoubleClick(rowData);
        this.loadDatas(null);
    }

    @FXML
    private void buttonDeleteClick(MouseEvent event) {
    }

    private void tableViewRowDoubleClick(DataEduCardInfo rowData) {
        Stage stage = new Stage();
        PageInfo pageInfo = PageUtil.getPageInfo("/ui/card/edit.fxml");
        stage.setScene(new Scene((GridPane) pageInfo.getNode()));
        stage.setTitle("会员卡信息");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.primaryStage.getScene().getWindow());
        stage.getIcons().add(PageUtil.getLogo());

        EditController contorller = (EditController) pageInfo.getController();
        contorller.setDataEduCardInfo(rowData);

        stage.showAndWait();
    }
}

package robot.client.controller.customer;

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
import robot.client.common.App;
import robot.client.common.PageInfo;
import robot.client.dao.CustomerDao;
import robot.client.model.customer.DataEduCustomerInfo;
import robot.client.util.PageUtil;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by Feng on 2017/5/18.
 */
public class ManagerContorller implements Initializable {

    @FXML
    private TableView tableViewCustomer;
    @FXML
    private TextField textFieldChildName;
    @FXML
    private Button btnSelect;
    @FXML
    private Button btnNew;
    @FXML
    private Button btnModity;
    @FXML
    private Button btnDelete;

    private ObservableList<DataEduCustomerInfo> dataSource = FXCollections.observableArrayList();


    public ManagerContorller() {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadDatas(null);

        this.tableViewCustomer.setRowFactory(tv -> {
            TableRow<DataEduCustomerInfo> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    DataEduCustomerInfo rowData = row.getItem();
//                    System.out.println(rowData);
                    tableViewRowDoubleClick(rowData);
                }
            });
            return row;
        });
    }

    private void loadDatas(String childName) {
        dataSource.clear();

        // select datas from db
        LinkedList<DataEduCustomerInfo> dataEduCardInfos = CustomerDao.selectDatas(childName);

        for (DataEduCustomerInfo info : dataEduCardInfos) {
            dataSource.add(info);
        }

        ObservableList<TableColumn> observableList = tableViewCustomer.getColumns();

        observableList.get(0).setCellValueFactory(new PropertyValueFactory("id"));
//        observableList.get(1).setCellValueFactory(new PropertyValueFactory("adviser"));
        observableList.get(1).setCellValueFactory(new PropertyValueFactory("cardNo"));
        observableList.get(2).setCellValueFactory(new PropertyValueFactory("childName"));
        observableList.get(3).setCellValueFactory(new PropertyValueFactory("childSex"));
        observableList.get(4).setCellValueFactory(new PropertyValueFactory("birthday"));
//        observableList.get(5).setCellValueFactory(new PropertyValueFactory("childImage"));
//        observableList.get(6).setCellValueFactory(new PropertyValueFactory("momName"));
//        observableList.get(7).setCellValueFactory(new PropertyValueFactory("momMobile"));
//        observableList.get(8).setCellValueFactory(new PropertyValueFactory("momEmail"));
//        observableList.get(9).setCellValueFactory(new PropertyValueFactory("dadName"));
//        observableList.get(10).setCellValueFactory(new PropertyValueFactory("dadMobile"));
//        observableList.get(11).setCellValueFactory(new PropertyValueFactory("dadEmail"));
        observableList.get(5).setCellValueFactory(new PropertyValueFactory("address"));
        observableList.get(6).setCellValueFactory(new PropertyValueFactory("remarks"));

        observableList.get(7).setCellValueFactory(new PropertyValueFactory("createTime"));
        observableList.get(8).setCellValueFactory(new PropertyValueFactory("updateTime"));

        tableViewCustomer.setItems(dataSource);
    }

    @FXML
    private void buttonSelectClick(MouseEvent event) {
        loadDatas(textFieldChildName.getText());
    }

    @FXML
    private void buttonNewClick(MouseEvent event) {
        Stage stage = new Stage();
//        GridPane dialog = PageUtil.getGridPane("/ui/customer/edit.fxml");
        PageInfo pageInfo = PageUtil.getPageInfo("/ui/customer/edit.fxml");
        stage.setScene(new Scene((GridPane) pageInfo.getNode()));
        stage.setTitle("添加宝宝信息");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.primaryStage.getScene().getWindow());
        stage.getIcons().add(PageUtil.getLogo());

        stage.showAndWait();

        this.loadDatas(null);
    }

    @FXML
    private void buttonModifyClick(MouseEvent event) {
        DataEduCustomerInfo rowData = (DataEduCustomerInfo) this.tableViewCustomer.getSelectionModel().getSelectedItem();
        tableViewRowDoubleClick(rowData);
    }

    @FXML
    private void buttonDeleteClick(MouseEvent event) {
    }

    private void tableViewRowDoubleClick(DataEduCustomerInfo rowData) {
        Stage stage = new Stage();
        PageInfo pageInfo = PageUtil.getPageInfo("/ui/customer/edit.fxml");
        stage.setScene(new Scene((GridPane) pageInfo.getNode()));
        stage.setTitle("宝宝信息");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.primaryStage.getScene().getWindow());
        stage.getIcons().add(PageUtil.getLogo());

        EditContorller contorller = (EditContorller) pageInfo.getController();
        contorller.setCustomerInfo(rowData);

        stage.showAndWait();
    }
}

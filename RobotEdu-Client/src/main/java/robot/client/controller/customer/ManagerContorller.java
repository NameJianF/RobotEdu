package robot.client.controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import robot.client.api.customer.CustomerApi;
import robot.client.common.App;
import robot.client.dao.CustomerDao;
import robot.client.model.customer.DataEduCustomerInfo;
import robot.client.model.customer.EduCustomerInfo;
import robot.client.observer.Subject;
import robot.client.util.PageUtil;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

/**
 * Created by Feng on 2017/5/18.
 */
public class ManagerContorller extends Subject implements Initializable {

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
        this.attach(new CustomerApi());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.loadDatas(null);
    }

    private void loadDatas(String childName) {

        // select datas from db
        LinkedList<DataEduCustomerInfo> dataEduCardInfos = CustomerDao.selectDatas(childName);

        for (DataEduCustomerInfo info : dataEduCardInfos) {
            dataSource.add(info);
        }

        ObservableList<TableColumn> observableList = tableViewCustomer.getColumns();

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

        tableViewCustomer.setItems(dataSource);
    }

    /**
     * 保存数据到数据库
     */
    private void insert() {
        EduCustomerInfo customerInfo = new EduCustomerInfo();
        this.nodifyObservers(customerInfo);
    }

    @FXML
    private void buttonSelectClick(MouseEvent event) {
        loadDatas(textFieldChildName.getText());
    }

    @FXML
    private void buttonNewClick(MouseEvent event) {
        Stage stage = new Stage();
        GridPane dialog = PageUtil.getGridPane("/ui/customer/edit.fxml");
        stage.setScene(new Scene(dialog));
        stage.setTitle("添加宝宝信息");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.primaryStage.getScene().getWindow());

        stage.getIcons().add(PageUtil.getLogo());
        stage.showAndWait();

        this.loadDatas(null);
    }

    @FXML
    private void buttonModifyClick(MouseEvent event) {
    }

    @FXML
    private void buttonDeleteClick(MouseEvent event) {
    }
}

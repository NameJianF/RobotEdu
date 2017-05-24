package robot.client.controller.customer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import robot.client.AppMain;
import robot.client.api.customer.CustomerApi;
import robot.client.dao.CustomerDao;
import robot.client.model.SexModel;
import robot.client.model.customer.EduCustomerInfo;
import robot.client.observer.Subject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Feng on 2017/5/18.
 */
public class EditContorller extends Subject implements Initializable {

    @FXML
    private ImageView imgChild;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    @FXML
    private TextField txtAdviser;
    @FXML
    private TextField txtCardNo;
    @FXML
    private TextField txtChildName;
    @FXML
    private ComboBox cmbChildSex;
    @FXML
    private DatePicker dateChildBirthday;
    @FXML
    private TextField txtMomName;
    @FXML
    private TextField txtMonMobile;
    @FXML
    private TextField txtMomEmail;
    @FXML
    private TextField txtDadName;
    @FXML
    private TextField txtDadMobile;
    @FXML
    private TextField txtDadEmail;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextArea txtRemarks;

    public EditContorller() {
        this.attach(new CustomerApi());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgChild.setImage(new Image(AppMain.class.getResource("/img/child.png").toExternalForm()));
        cmbChildSex.setItems(SexModel.getItemList());
    }


    /**
     * 保存数据到数据库
     */
    private void insert() {
        SexModel sex = (SexModel) cmbChildSex.getSelectionModel().getSelectedItem();

        EduCustomerInfo customerInfo = new EduCustomerInfo();
        customerInfo.setAdviser(txtAdviser.getText());
        customerInfo.setCardNo(Long.valueOf(txtCardNo.getText()));
        customerInfo.setChildName(txtChildName.getText());
        customerInfo.setChildSex(sex.getValue());
        customerInfo.setBirthday(dateChildBirthday.getValue().toString());
        customerInfo.setChildImage("");
        customerInfo.setMomName(txtMomName.getText());
        customerInfo.setMomMobile(txtMonMobile.getText());
        customerInfo.setMomEmail(txtMomEmail.getText());
        customerInfo.setDadName(txtDadName.getText());
        customerInfo.setDadMobile(txtDadMobile.getText());
        customerInfo.setDadEmail(txtDadEmail.getText());
        customerInfo.setAddress(txtAddress.getText());
        customerInfo.setRemarks(txtRemarks.getText());
        customerInfo.setCreateTime(System.currentTimeMillis());
        customerInfo.setUpdateTime(customerInfo.getCreateTime());

        // insert to db
        Integer ret = CustomerDao.insert(customerInfo);
        if (ret > 0) {
            // upload
            this.nodifyObservers(customerInfo);
        }
    }

    @FXML
    private void buttonSaveClick(MouseEvent event) {
        insert();
    }

    @FXML
    private void buttonCancelClick(MouseEvent event) {
        close();
    }

    /**
     * close this window
     */
    private void close() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        // do what you have to do
        stage.close();
    }

}

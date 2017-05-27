package robot.client.controller.customer;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import robot.client.AppMain;
import robot.client.api.customer.CustomerApi;
import robot.client.common.DataOp;
import robot.client.dao.CustomerDao;
import robot.client.model.SexModel;
import robot.client.model.customer.DataEduCustomerInfo;
import robot.client.model.customer.EduCustomerInfo;
import robot.client.observer.Subject;
import robot.client.util.DateTimeUtil;

import java.net.URL;
import java.time.LocalDate;
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

    private DataEduCustomerInfo customerInfo;

    public EditContorller() {
        this.attach(new CustomerApi());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgChild.setImage(new Image(AppMain.class.getResource("/img/child.png").toExternalForm()));
        cmbChildSex.setItems(SexModel.getItemList());
    }

    private void loadCustomerInfo() {
        txtAdviser.setText(this.customerInfo.getAdviser());
        txtCardNo.setText(String.valueOf(this.customerInfo.getCardNo()));
        txtChildName.setText(this.customerInfo.getChildName());
        for (SexModel model : SexModel.getItemList()) {
            if (model.getText().equals(this.customerInfo.getChildSex())) {
                cmbChildSex.getSelectionModel().select(model);
                break;
            }
        }
        dateChildBirthday.setValue(DateTimeUtil.getLocalDate(this.customerInfo.getBirthday()));
        txtMomName.setText(this.customerInfo.getMomName());
        txtMonMobile.setText(this.customerInfo.getMomMobile());
        txtMomEmail.setText(this.customerInfo.getMomEmail());
        txtDadName.setText(this.customerInfo.getDadName());
        txtDadMobile.setText(this.customerInfo.getDadMobile());
        txtDadEmail.setText(this.customerInfo.getDadEmail());
        txtAddress.setText(this.customerInfo.getAddress());
        txtRemarks.setText(this.customerInfo.getRemarks());
    }

    @FXML
    private void buttonSaveClick(MouseEvent event) {
        if (StringUtils.isEmpty(txtAdviser.getText())) {
            return;
        }
        if (StringUtils.isEmpty(txtChildName.getText())) {
            return;
        }
        if (cmbChildSex.getSelectionModel().getSelectedItem() == null) {
            return;
        }

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
        if (this.customerInfo == null) {
            insert(customerInfo);
        } else {
            modify(customerInfo);
        }

        this.close();
    }

    /**
     * 保存数据到数据库
     */
    private void insert(EduCustomerInfo customerInfo) {
        customerInfo.setCreateTime(System.currentTimeMillis());
        customerInfo.setUpdateTime(customerInfo.getCreateTime());

        // insert to db
        Integer ret = CustomerDao.insert(customerInfo);
        if (ret > 0) {
            // upload
            customerInfo.setId(ret);
            this.nodifyObservers(customerInfo, DataOp.INSERT);
        }
    }

    private void modify(EduCustomerInfo customerInfo) {
        customerInfo.setId(this.customerInfo.getId());
        customerInfo.setUpdateTime(System.currentTimeMillis());

        // update to db
        Integer ret = CustomerDao.update(customerInfo);
        if (ret > 0) {
            // upload
            this.nodifyObservers(customerInfo, DataOp.MODIFY);
        }
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


    public DataEduCustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(DataEduCustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
        loadCustomerInfo();
    }
}

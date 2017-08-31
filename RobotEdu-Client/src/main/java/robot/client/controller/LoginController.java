package robot.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import robot.client.api.staff.StaffApi;
import robot.client.common.ErrorCode;
import robot.client.observer.Subject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Feng on 2017/5/5.
 */
public class LoginController extends Subject implements Initializable {
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPwd;
    @FXML
    Button btnLogin;
    @FXML
    Button btnCancel;

    public LoginController() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void loginClick(MouseEvent event) {
        StaffApi staffApi = new StaffApi();

        ErrorCode code = staffApi.login(txtUserName.getText(), txtPwd.getText());
        if (ErrorCode.SUCCESS.equals(code)) {
            this.close();
        }
    }

    @FXML
    public void cancelClick(MouseEvent event) {
        this.close();
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

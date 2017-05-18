package robot.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import robot.client.observer.Subject;

/**
 * Created by Feng on 2017/5/5.
 */
public class LoginController extends Subject {
    @FXML
    Button btnLogin;
    @FXML
    Button btnCancel;

    public LoginController() {
    }

    @FXML
    public void loginClick(MouseEvent event) {

    }

    @FXML
    public void cancelClick(MouseEvent event) {

    }
}

package robot.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import robot.client.common.App;
import robot.client.common.Config;
import robot.client.util.PageUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane paneMainCenter;
    @FXML
    private Button btnSwipeManager;
    @FXML
    private Button btnCustomerManager;
    @FXML
    private Button btnStaffManager;
    @FXML
    private Button btnSettingManager;
    @FXML
    private Label txtUser;

    private BorderPane cardsBorderPane;
    private BorderPane customerBorderPane;
    private BorderPane staffBorderPane;
    private BorderPane swipeBorderPane;
    private BorderPane currentBorderPane;

    private final String FLAG_CARD = "card";
    private final String FLAG_CUSTOMER = "customer";
    private final String FLAG_STAFF = "staff";
    private final String FLAG_SWIPE = "swipe";

    public MainController() {
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeMainBorderPane(FLAG_SWIPE);
    }

    private void changeMainBorderPane(String flag) {
        currentBorderPane = getBorderPane(flag);
        if (currentBorderPane != null) {
            paneMainCenter.getChildren().clear();
            paneMainCenter.setTopAnchor(currentBorderPane, 10D);
            paneMainCenter.setRightAnchor(currentBorderPane, 10D);
            paneMainCenter.setBottomAnchor(currentBorderPane, 10D);
            paneMainCenter.setLeftAnchor(currentBorderPane, 10D);
            paneMainCenter.getChildren().add(currentBorderPane);
        }
    }

    private BorderPane getBorderPane(String flag) {
        if (FLAG_CARD.equalsIgnoreCase(flag)) {
            if (cardsBorderPane == null) {
                cardsBorderPane = (BorderPane) PageUtil.getPageInfo("/ui/card/manager.fxml").getNode();
            }
            return cardsBorderPane;
        } else if (FLAG_CUSTOMER.equalsIgnoreCase(flag)) {
            if (customerBorderPane == null) {
                customerBorderPane = (BorderPane) PageUtil.getPageInfo("/ui/customer/manager.fxml").getNode();
            }
            return customerBorderPane;
        } else if (FLAG_STAFF.equalsIgnoreCase(flag)) {
            if (staffBorderPane == null) {
                staffBorderPane = (BorderPane) PageUtil.getPageInfo("/ui/staff/manager.fxml").getNode();
            }
            return staffBorderPane;
        } else if (FLAG_SWIPE.equalsIgnoreCase(flag)) {
            if (swipeBorderPane == null) {
                swipeBorderPane = (BorderPane) PageUtil.getPageInfo("/ui/swipe/manager.fxml").getNode();
            }
            return swipeBorderPane;
        }
        return null;
    }

    @FXML
    private void swipeManagerClick(MouseEvent event) {
        changeMainBorderPane(FLAG_SWIPE);
    }

    @FXML
    private void customerManagerClick(MouseEvent event) {
        if (this.checkLogin()) {
            changeMainBorderPane(FLAG_CUSTOMER);
        }
    }

    @FXML
    private void staffManagerClick(MouseEvent event) {
        if (this.checkLogin()) {
            changeMainBorderPane(FLAG_STAFF);
        }
    }

    @FXML
    private void settingManagerClick(MouseEvent event) {
        if (this.checkLogin()) {
            changeMainBorderPane(FLAG_CARD);
        }
    }

    private Boolean checkLogin() {
        if (Config.LoginUser == null) {
            return this.showLoginWindow();
        }

        return true;
    }

    private Boolean showLoginWindow() {
        Stage stage = new Stage();
        AnchorPane dialog = (AnchorPane) PageUtil.getPageInfo("/ui/login.fxml").getNode();
        stage.setScene(new Scene(dialog));
        stage.setTitle("登录");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.primaryStage.getScene().getWindow());
        stage.getIcons().add(PageUtil.getLogo());
        stage.showAndWait();

        if (Config.LoginUser == null) {
            return false;
        }
        txtUser.setText(String.format("当前登录账号：%s", Config.LoginUser.getEmail()));
        return true;
    }
}

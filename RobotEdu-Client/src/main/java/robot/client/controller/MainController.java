package robot.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;
import robot.client.common.App;
import robot.client.common.Config;
import robot.client.util.PageUtil;
import robot.client.util.PropertiesUtils;

import java.net.URL;
import java.util.Optional;
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
    @FXML
    private Label labelBottom;

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

    public void initialize(URL location, ResourceBundle resources) {
        // 检查客户端配置信息
        checkClientInfo();

        labelBottom.setText(String.format("扉渡国际教育-管理系统(V0.2) >>> %s ", Config.CLIENT_NAME));
        changeMainBorderPane(FLAG_SWIPE);
    }

    private void checkClientInfo() {
        if (StringUtils.isEmpty(Config.CLIENT_CODE)
                || StringUtils.isEmpty(Config.CLIENT_NAME)
                || StringUtils.isEmpty(Config.MODULE_APP_KEY)) {
            createCustomerDialog();
            if (StringUtils.isEmpty(Config.CLIENT_CODE)
                    || StringUtils.isEmpty(Config.CLIENT_NAME)
                    || StringUtils.isEmpty(Config.MODULE_APP_KEY)) {
                // 退出系统
                App.primaryStage.close();
                System.exit(0);
            }
        }
    }

    private void createCustomerDialog() {
        // Create the custom dialog.
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("配置");
        dialog.setHeaderText("门店信息配置");

        // Set the button types.
        ButtonType saveButtonType = new ButtonType("保存", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField textFieldCode = new TextField();
        textFieldCode.setPromptText("Code");
        TextField textFieldName = new TextField();
        textFieldName.setPromptText("Name");
        TextField textFieldKey = new TextField();
        textFieldName.setPromptText("key");

        grid.add(new Label("门店编号:"), 0, 0);
        grid.add(textFieldCode, 1, 0);
        grid.add(new Label("门店名称:"), 0, 1);
        grid.add(textFieldName, 1, 1);
        grid.add(new Label("Key:"), 0, 2);
        grid.add(textFieldKey, 1, 2);

        // Enable/Disable login button depending on whether a username was entered.
        Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        textFieldCode.textProperty().addListener((observable, oldValue, newValue) -> {
            saveButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> textFieldCode.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                Config.CLIENT_CODE = textFieldCode.getText();
                Config.CLIENT_NAME = textFieldName.getText();
                Config.MODULE_APP_KEY = textFieldKey.getText();
                PropertiesUtils.writeClientProperties(Config.CLIENT_CODE, Config.CLIENT_NAME, Config.MODULE_APP_KEY);

                return new Pair<>(textFieldCode.getText(), textFieldName.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(codename -> {
            System.err.println(String.format("code:%s,name%s", codename.getKey(), codename.getValue()));
        });
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

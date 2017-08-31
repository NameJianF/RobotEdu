package robot.client.controller.update;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;
import robot.client.common.App;
import robot.client.updater.Updater;


public class UpdateController extends AnchorPane implements Initializable, Updater.UpdaterListener {

    static Logger log = Logger.getLogger(UpdateController.class);

    @FXML
    private ProgressBar progressBar;
    @FXML
    Label label;

    @FXML
    Label downLab;

    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;


    private void fileInfo(String fn, int fileCount, int fileIndex) {
        // reset
        progressBar.setProgress(0);
        String info = String.format(" (%s/%s)", fileIndex, fileCount);
        setLabelTextStyle(downLab, info);

    }

    private void processInfo(long length, long transferred) {
        if (length > 0) {
            double d = transferred / (double) length;
            progressBar.setProgress(d);
        }
    }

    private void hidenWindow() {
        this.setVisible(false);
        setLabelTextStyle(label, "程序更新完成，请重新启动");
        downLab.setVisible(false);
    }

    public static void setLabelTextStyle(Label label, String txt) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                label.setText(txt);
                label.setStyle("-fx-font-size:12px;-fx-font-family:\"Microsoft Yahei\",Tahoma,Arial,Helvetica,STHeiti;");
            }
        });

    }

    private void closeWindow() {
        App.primaryStage.close();
        System.exit(0);
    }

    private void setUpdateStatus() {
        setLabelTextStyle(label, "程序更新错误,请重新启动!");
    }

    @FXML
    public void updateOnMouseDragged(MouseEvent event) {
        if (App.primaryStage == null)
            return;
        App.primaryStage.setX(
                event.getScreenX() - mouseDragOffsetX);
        App.primaryStage.setY(
                event.getScreenY() - mouseDragOffsetY);
    }

    @FXML
    public void updateOnMousePressed(MouseEvent event) {
        if (App.primaryStage == null)
            return;
        mouseDragOffsetX = event.getSceneX();
        mouseDragOffsetY = event.getSceneY();
    }

    public void initialize(URL location, ResourceBundle resources) {
        downLab.setVisible(true);
        
    }

    @FXML
    public void miniWindow(MouseEvent event) {
        App.primaryStage.setIconified(
                !App.primaryStage.isIconified());
    }

    @FXML
    public void updateCloseWindow(MouseEvent event) {
        exitWindow();
    }

    public void update() {
        Updater.getInstance().setUpdaterListener(this);
        Thread t = new Thread("Updater") {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    boolean res = Updater.getInstance().update();
                    if (res) {
                        hidenUpdate();
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    exitWindow();
                } finally {
                }
            }
        };
        t.start();
    }

    private void hidenUpdate() {
        hidenWindow();
    }

    private void exitWindow() {
        closeWindow();
    }

    public void download(String fn, int fileCount, int fileIndex) {
        fileInfo(fn, fileCount, fileIndex);
    }

    public void transferred(long length, long transferred) {
        processInfo(length, transferred);
    }

    public void updateError() {
        setUpdateStatus();
    }


}

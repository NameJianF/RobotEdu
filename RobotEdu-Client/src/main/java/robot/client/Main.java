package robot.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import robot.client.api.staff.StaffApi;
import robot.client.util.Logger;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        URL uri = getClass().getResource("/ui/main.fxml");

        Parent root = FXMLLoader.load(uri);
        primaryStage.setTitle("Robot-Edu");
        primaryStage.setScene(new Scene(root, 500, 400));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public static void main(String[] args) {

        Logger.debug(" >>>>>> Application Start ... <<<<<< ");

        new StaffApi().login("fjf789@126.com", "123456");
        launch(args);
    }


}

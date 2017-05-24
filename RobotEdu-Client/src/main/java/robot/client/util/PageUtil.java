package robot.client.util;

import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import robot.client.AppMain;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Feng on 2017/5/23.
 */
public class PageUtil {

    public static BorderPane getBorderPane(String fxml) {
        Logger.debug("FXML path:" + fxml);
        URL url = AppMain.class.getResource(fxml);
        FXMLLoader loader = new FXMLLoader();// 创建对象
        loader.setBuilderFactory(new JavaFXBuilderFactory());// 设置BuilderFactory
        loader.setLocation(url);  // 设置路径基准
        BorderPane page = null;
        InputStream in = null;
        try {
            in = url.openStream();
            page = loader.load(in);  // 对象方法的参数是InputStream，返回值是Object
        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                Logger.error(e.getMessage(), e);
            }
        }

        return page;
    }


    public static GridPane getGridPane(String fxml) {
        Logger.debug("FXML path:" + fxml);
        URL url = AppMain.class.getResource(fxml);
        FXMLLoader loader = new FXMLLoader();// 创建对象
        loader.setBuilderFactory(new JavaFXBuilderFactory());// 设置BuilderFactory
        loader.setLocation(url);  // 设置路径基准
        GridPane page = null;
        InputStream in = null;
        try {
            in = url.openStream();
            page = loader.load(in);  // 对象方法的参数是InputStream，返回值是Object

        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                Logger.error(e.getMessage(), e);
            }
        }

        return page;
    }

}

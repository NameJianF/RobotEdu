package robot.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import robot.client.common.App;
import robot.client.common.Config;
import robot.client.common.PageInfo;
import robot.client.controller.update.UpdateController;
import robot.client.db.DbHelper;
import robot.client.service.DatabaseService;
import robot.client.service.SystemService;
import robot.client.updater.Updater;
import robot.client.util.Logger;
import robot.client.util.PageUtil;
import robot.client.util.PropertiesUtils;

import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class AppMain extends Application {
    /**
     * load system config
     */
    static {
        Logger.debug(" >>>>>> Application LoadConfig Start ... <<<<<< ");
        loadConfig();
        checkUpdate();
        Logger.debug(" >>>>>> Application LoadConfig End ... <<<<<< ");
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        App.primaryStage = primaryStage;

        URL url = getClass().getResource("/ui/main.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("扉渡国际教育");
        primaryStage.setScene(new Scene(root, 1000, 600));
//        primaryStage.setFullScreen(true);
        primaryStage.getIcons().add(PageUtil.getLogo());
        primaryStage.show();
    }


    public static void main(String[] args) throws SQLException {
        Logger.debug(" >>>>>> Application Start ... <<<<<< ");
        // 加载客户端信息
        PropertiesUtils.readClientProperties();

        // 初始化数据库
        DbHelper.initConnection();
        // 初始化系统服务
        SystemService.getInstance().init();

        // 启动后，执行一次数据上报
        DatabaseService.getInstance().init();
//        DbHelper.testDb();
        launch(args);

        Logger.debug(" >>>>>> Application Closed ... <<<<<< ");
        System.exit(0);
    }

    /**
     * 加载配置文件
     */
    private static void loadConfig() {
        Properties prop = new Properties();
//        URL uri = AppMain.class.getClass().getResource("/config.properties");
//        String fileName = uri.getFile();

        try {

//            InputStream in = new BufferedInputStream(new FileInputStream(fileName));
//            prop.load(in);

            // 最前面有斜杠
            InputStream input = AppMain.class.getClass().getResourceAsStream("/config.properties");
            prop.load(input);
        } catch (Exception ex) {
            Logger.error(ex.getMessage(), new Throwable(ex));
        }

        Config.SERVER_URL = prop.getProperty("server.url");
//        Config.MODULE_APP_KEY = prop.getProperty("client.apikey");
        Config.MODULE_APP_SECRET = prop.getProperty("client.secret");
        Config.MODULE_SOURCE = prop.getProperty("client.source");
        Config.CLIENT_VERSION = prop.getProperty("client.version");


        // mysql
        Config.JDBC_URL = prop.getProperty("jdbc.url");
        Config.JDBC_USERNAME = prop.getProperty("jdbc.username");
        Config.JDBC_PASSWORD = prop.getProperty("jdbc.password");

        // redis
        Config.REDIS_IP = prop.getProperty("redis.host");
        Config.REDIS_PORT = prop.getProperty("redis.port");
        Config.REDIS_INDEX = prop.getProperty("redis.index");

        Config.REDIS_MAX_IDLE = prop.getProperty("redis.maxIdle");
        Config.REDIS_MAX_WAIT = prop.getProperty("redis.maxWait");
        Config.REDIS_TEST_ON_BORROW = prop.getProperty("redis.testOnBorrow");

        Config.printValues();
    }


    private static void checkUpdate() {
        Thread t = new Thread("UpdateChecker") {
            @Override
            public void run() {
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 检查是否需要更新
                Logger.info("Checking new version of feidu.");
                if (Updater.getInstance().isUpdateNeeded()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("在线更新");
                            alert.setHeaderText("扉渡客户在线更新");
                            alert.setContentText(Updater.getInstance().getUpdateChecker().getRemotePublishInfo());

                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                // ... user chose OK
                                doUpdate();
                            }
//                    else {
//                        // ... user chose CANCEL or closed the dialog
//                    }

                        }
                    });
                }
            }
        };
        t.start();
    }

    private static void doUpdate() {
        String updateXml = "/ui/update/update.fxml";

        final PageInfo updatePage;
        updatePage = PageUtil.getPageInfo(updateXml);

        Scene scene = new Scene((Parent) updatePage.getNode());
        App.primaryStage.setScene(scene);
        App.primaryStage.centerOnScreen();
        App.primaryStage.sizeToScene();
        final UpdateController updateController = (UpdateController) updatePage.getController();

        if (Platform.isFxApplicationThread()) {
            updateController.update();
        } else {
            Platform.runLater(new Runnable() {
                public void run() {
                    updateController.update();
                }
            });
        }
    }
}

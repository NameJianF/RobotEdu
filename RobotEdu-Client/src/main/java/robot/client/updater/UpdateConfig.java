package robot.client.updater;

import java.io.File;

import org.apache.log4j.Logger;

public class UpdateConfig {

    static Logger log = Logger.getLogger(UpdateConfig.class);
    private static String serverUrl = "http://47.94.236.83/update/clientConfig.xml";
    private static String clientDat;
    private static String clientDatOld;
    private static String updateLog;
    private static String applicationPath;
    private static String tempDir;
    private static String backupDir;


    static {
        doPrepare();
    }

    /**
     * 初始化目录函数
     */
    private static void doPrepare() {
        applicationPath = System.getProperty("user.dir");
        /*
         * File.separator 分割路径
		 */
        clientDat = applicationPath + File.separator + "configuration" + File.separator + "mClient.dat";
        clientDatOld = applicationPath + File.separator + "mClient.dat";

        updateLog = applicationPath + File.separator + "auto_update" + File.separator + "update.log";

        tempDir = applicationPath + File.separator + "auto_update" + File.separator + "temp";

        backupDir = applicationPath + File.separator + "auto_update" + File.separator + "backup";

        File f = new File(tempDir);
        f.mkdirs();
        f = new File(backupDir);
        f.mkdirs();
    }

    /**
     * 获取远端更新地址
     *
     * @return
     */
    public static String getServerUrl() {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            log.info("Windows: " + serverUrl);
            return serverUrl;
        }
//        else if (osName.startsWith("Mac")) {
//            log.info("Mac: " + serverMacUrl);
//            return serverMacUrl;
//        }
        log.info("未知系统：" + serverUrl);
        return serverUrl;
    }

    /**
     * 获取mClient.dat所在路径
     *
     * @return
     */
    public static String getClientDat() {
        return clientDat;
    }

    public static String getClientDatOld() {
        return clientDatOld;
    }

    /**
     * 获取更新日志
     *
     * @return
     */
    public static String getUpdateLog() {
        return updateLog;
    }

    /**
     * 获取应用程序所在目录
     *
     * @return
     */
    public static String getApplicationPath() {
        return applicationPath;
    }

    /**
     * 获取更新临时目录
     *
     * @return
     */
    public static String getTempDir() {
        return tempDir;
    }

    /**
     * 获取备份目录
     *
     * @return
     */
    public static String getBackupDir() {
        return backupDir;
    }

}

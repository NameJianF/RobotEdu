package robot.client.common;

import robot.client.model.staff.User;

/**
 * Created by Feng on 2017/5/12.
 */
public class Config {

    public static String SERVER_URL;
    public static String MODULE_APP_SECRET;
    public static String MODULE_APP_KEY;

    public static String MODULE_SOURCE;
    public static String CLIENT_VERSION;

    // client config
    public static String CLIENT_CODE;
    public static String CLIENT_NAME;

    //mysql
    public static String JDBC_URL;
    public static String JDBC_USERNAME;
    public static String JDBC_PASSWORD;

    //redis
    public static String REDIS_IP;
    public static String REDIS_PORT;
    public static String REDIS_INDEX;
    public static String REDIS_MAX_IDLE;
    public static String REDIS_MAX_WAIT;
    public static String REDIS_TEST_ON_BORROW;


    public static User LoginUser;


    public static void printValues() {
        System.err.println(String.format("SERVER_URL=%s,MODULE_APP_SECRET=%s,MODULE_APP_KEY=%s,MODULE_SOURCE=%s,CLIENT_VERSION=%s," +
                        "REDIS_IP=%s,REDIS_PORT=%s,REDIS_INDEX=%s,REDIS_MAX_IDLE=%s,REDIS_MAX_WAIT=%s,=%s"
                , SERVER_URL, MODULE_APP_SECRET, MODULE_APP_KEY, MODULE_SOURCE, CLIENT_VERSION,
                REDIS_IP, REDIS_PORT, REDIS_INDEX, REDIS_MAX_IDLE, REDIS_MAX_WAIT, REDIS_TEST_ON_BORROW));
    }
}

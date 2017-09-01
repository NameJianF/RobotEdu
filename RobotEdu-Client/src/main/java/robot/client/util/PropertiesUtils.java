package robot.client.util;

import robot.client.common.Config;

import java.io.*;
import java.util.Date;
import java.util.Properties;

public class PropertiesUtils {

    public static void writeClientProperties(String code, String name, String apiKey) {
        Properties properties = new Properties();
        OutputStream output = null;
        try {
            output = new FileOutputStream("client.properties");
            properties.setProperty("client.code", code);
            properties.setProperty("client.name", name);
            properties.setProperty("client.apikey", apiKey);
            properties.store(output, "modify" + new Date().toString());// 保存键值对到文件中
        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void readClientProperties() {
        Properties properties = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("client.properties");//加载Java项目根路径下的配置文件
            properties.load(input);// 加载属性文件
            Config.CLIENT_CODE = properties.getProperty("client.code");
            Config.CLIENT_NAME = properties.getProperty("client.name");
            Config.MODULE_APP_KEY = properties.getProperty("client.apikey");
        } catch (IOException io) {
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

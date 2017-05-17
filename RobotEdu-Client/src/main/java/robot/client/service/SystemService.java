package robot.client.service;

import org.apache.commons.lang3.StringUtils;
import robot.client.api.http.HttpUtils;
import robot.client.util.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by Feng on 2017/5/17.
 * <p>
 * 系统服务：
 * 1.数据上报
 * 2.数据下载
 */
public class SystemService {

    private static SystemService instance;

    public static SystemService getInstance() {
        if (instance == null) {
            instance = new SystemService();
        }

        return instance;
    }

    private BlockingQueue<UploadDatas> queueUploadDatas = new LinkedBlockingDeque<>(10);

    private Thread threadUploadDatas;
    private Thread threadDownloadDatas;

    public void init() {
        threadUploadDatas = new Thread(new Runnable() {
            @Override
            public void run() {
                uploadDatas();
            }
        }, "threadUploadDatas");
        threadUploadDatas.start();

        threadDownloadDatas = new Thread(new Runnable() {
            @Override
            public void run() {
                downloadDatas();
            }
        }, "threadDownloadDatas");
        threadDownloadDatas.start();
    }

    public void addUploadDatas(UploadDatas data) {
        queueUploadDatas.add(data);
    }

    private void uploadDatas() {
        try {
            UploadDatas data = queueUploadDatas.take();
            if (data != null) {
                Logger.info(String.format("URL:%s,Request JSON:%s", data.getUrl(), data.getJson()));
                String strResponse = HttpUtils.httpRequest(data.getUrl(), HttpUtils.REQUEST_METHOD_POST, data.getJson());
                Logger.info(String.format("URL:%s,Response JSON:%s", data.getUrl(), strResponse));
            }
        } catch (InterruptedException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    private void downloadDatas() {

    }

    public static class UploadDatas {
        private String url;
        private String json;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getJson() {
            return json;
        }

        public void setJson(String json) {
            this.json = json;
        }
    }
}

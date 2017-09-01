package robot.client.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import robot.client.api.http.HttpUtils;
import robot.client.common.DataOp;
import robot.client.common.ErrorCode;
import robot.client.db.DbHelper;
import robot.client.util.Logger;

import java.sql.SQLException;
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

    private Boolean stopUploadDatas = false;
    private Boolean stopDownloadDatas = false;

    public void init() {
        threadUploadDatas = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopUploadDatas) {
                    uploadDatas();
                }
            }
        }, "threadUploadDatas");
        threadUploadDatas.start();

        threadDownloadDatas = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopDownloadDatas) {
                    downloadDatas();
                }
            }
        }, "threadDownloadDatas");
        threadDownloadDatas.start();
    }

    public void addUploadDatas(UploadDatas data) {
        queueUploadDatas.add(data);
    }

    private void uploadDatas() {
        try {
            UploadDatas data = null;
            data = queueUploadDatas.take();
            if (data != null) {
                Logger.info(String.format("URL:%s,Request JSON:%s", data.getUrl(), data.getJson()));
                String strResponse = HttpUtils.httpRequest(data.getUrl(), HttpUtils.REQUEST_METHOD_POST, data.getJson());
                Logger.info(String.format("URL:%s,Response JSON:%s", data.getUrl(), strResponse));

                JSONObject jsonObject = JSON.parseObject(strResponse);
                if (jsonObject != null && jsonObject.containsKey("code")) {
                    if (ErrorCode.SUCCESS.getCode().equals(jsonObject.getInteger("code"))) {
                        // update database
                        String sql = String.format(" update %s set upload = '1' where id = %s", data.getTableName(), data.getRowId());
                        try {
                            int ret = DbHelper.update(sql);
                            if (ret <= 0) {
                                Logger.debug("sql exec error: " + sql);
                            }
                        } catch (SQLException e) {
                            Logger.debug(e.getMessage(), e);
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            Logger.error(e.getMessage(), e);
        }
    }

    /**
     * 停止上传数据
     */
    public void stopUpload() {
        this.stopUploadDatas = true;
    }


    private void downloadDatas() {

    }

    /**
     * 停止下载数据
     */
    public void stopDownload() {
        this.stopDownloadDatas = true;
    }


    public static class UploadDatas {
        private String url;
        private String json;
        private String tableName;
        private Integer rowId;
        private DataOp dataOp;

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

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public Integer getRowId() {
            return rowId;
        }

        public void setRowId(Integer rowId) {
            this.rowId = rowId;
        }

        public DataOp getDataOp() {
            return dataOp;
        }

        public void setDataOp(DataOp dataOp) {
            this.dataOp = dataOp;
        }
    }
}

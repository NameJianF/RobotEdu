package robot.client.updater;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class HttpDownloader {

    static Logger log = Logger.getLogger(HttpDownloader.class);

    static final int TIMEOUT = 1000 * 60 * 5;// 从202下载的任务超时时间设置为5分钟

    public HttpDownloader() {

    }

    /**
     * 下载文件
     *
     * @param url
     * @param local
     * @param onDlStateChangeListener
     * @return
     */
    public static DownloadFile download(String url, String local,
                                        OnDownloadStateChangeListener onDlStateChangeListener) {
        int statusCode = 0, contentLength = 0, transferred = 0;
        // 准备开始
        int state = DownloadState.NotStarted;
        notifyAllListeners(onDlStateChangeListener, state, contentLength,
                transferred);//

        // 当前下载文件内容
        DownloadFile dlFile = new DownloadFile();
        dlFile.setUrl(url);
        log.info(url);
        dlFile.setLocal(local);
        log.info(local);

        HttpURLConnection httpConn = null;
        FileOutputStream fos = null;
        BufferedInputStream in = null;
        try {
            state = DownloadState.Downloading;

            URL uri = new URL(url);
            log.info(uri);
            httpConn = (HttpURLConnection) uri.openConnection();
            httpConn.setConnectTimeout(300000);
            httpConn.setReadTimeout(300000);
            // httpConn.setRequestProperty("Connection", "close");
            // httpConn.setDoOutput(true);
            // httpConn.setDoInput(true);

            httpConn.setRequestMethod("GET");

            httpConn.connect();// 打开连接
            statusCode = httpConn.getResponseCode();
            contentLength = httpConn.getContentLength();
            notifyAllListeners(onDlStateChangeListener, state, contentLength,
                    transferred);//
            transferred = 0;
            if (statusCode == HttpURLConnection.HTTP_OK) {
                in = new BufferedInputStream(httpConn.getInputStream());
                fos = new FileOutputStream(local);

                byte[] buff = new byte[4 * 1024];
                int size = 0;
                while ((size = in.read(buff)) != -1) {
                    fos.write(buff, 0, size);
                    transferred += size;
                    notifyAllListeners(onDlStateChangeListener, state,
                            contentLength, transferred);//
                }
                in.close();
                fos.close();
                in = null;
                fos = null;

                state = DownloadState.Sucess;//
            } else {
                state = DownloadState.Failure;// 状态码有误
                log.info(DownloadState.Failure);
                dlFile.setErrorMsg("下载失败,状态码错误");
            }
            httpConn.disconnect();
            httpConn = null;
        } catch (IOException ex) {
            state = DownloadState.Error;// 下载错误
            dlFile.setErrorMsg("下载失败,服务器错误");
            dlFile.setStacktrace(getErrorInfo(ex));
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }

            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        notifyAllListeners(onDlStateChangeListener, state, contentLength,
                transferred);//
        dlFile.setStatusCode(statusCode);
        dlFile.setState(state);
        dlFile.setMd5("");
        return dlFile;
    }

    private static String getErrorInfo(Throwable arg1) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        arg1.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }

    /**
     * 通知监听器
     */
    private static void notifyAllListeners(OnDownloadStateChangeListener listener,
                                           int state, int contentLength, int transferred) {
        if (listener != null)
            listener.onDlStateChange(state, contentLength, transferred);
    }
}

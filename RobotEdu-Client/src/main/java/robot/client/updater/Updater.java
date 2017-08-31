package robot.client.updater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;

public class Updater implements OnDownloadStateChangeListener {

    static Logger log = Logger.getLogger(Updater.class);

    public interface UpdaterListener {
        public void download(String fn, int fileCount, int fileIndex);

        public void transferred(long length, long transferred);

        public void updateError();
    }

    private static Updater update = new Updater();
    private UpdaterListener updaterListener;
    private UpdateChecker updateChecker = new UpdateChecker();

    // need updated files
    private List<DownloadFile> dlFiles = new ArrayList<DownloadFile>();
    // need backup files
    private List<String> backupFiles = new ArrayList<String>();

    private Updater() {
    }

    public static Updater getInstance() {
        return update;
    }

    public void setUpdaterListener(UpdaterListener updaterListener) {
        this.updaterListener = updaterListener;
    }

    /**
     * is need update
     *
     * @return
     */
    public boolean isUpdateNeeded() {
        return updateChecker.isNeedUpdate();
    }


    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }


    /**
     * auto update
     */
    public boolean update() {
        boolean res = false;
        try {
            res = downloadFiles();
            if (res) {
                backupFiles();
                boolean delSuccess = delDiscardFile();
                boolean unzipSuccess = unzipFiles();
                boolean copySuccess = copyFiles();
                System.err.println("del: " + delSuccess + " unzip: " + unzipSuccess + " copy: " + copySuccess);
                if (delSuccess && unzipSuccess && copySuccess) {
                    updateLocalXml();
                    res = true;
                } else {
                    rollbackFiles();
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return res;
    }

    /**
     * Deal with updated files that need unzip. Support tar.gz (for mac and
     * Ubuntu), and zip (mainly for windows)
     */
    private boolean unzipFiles() {
        boolean success = true;
        for (DownloadFile dl : this.dlFiles) {
            if (dl.isUnzip() && StringUtils.isNotEmpty(dl.getLocal())) {
                Archiver archiver = null;
                if (dl.getLocal().toLowerCase().endsWith(".zip")) {
                    archiver = ArchiverFactory.createArchiver(ArchiveFormat.ZIP);
                } else if (dl.getLocal().toLowerCase().endsWith(".tar.gz")) {
                    archiver = ArchiverFactory.createArchiver(ArchiveFormat.TAR, CompressionType.GZIP);
                }
                if (archiver == null) {
                    continue;
                }
                File zipFile = new File(dl.getLocal());
                String destPath = System.getProperty("user.dir") + File.separator + dl.getPath();
                File destDir = new File(destPath);
                try {
                    archiver.extract(zipFile, destDir);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    success = false;
                    break;
                }
            }
        }
        return success;
    }

    /**
     * delete files
     *
     * @return
     */
    private boolean delDiscardFile() {
        boolean success = true;
        try {
            List<RemoteFile> discardFiles = UpdateChecker.getDiscardFiles();
            for (RemoteFile rf : discardFiles) {
                String path = rf.getPath().replace(" ", "");
                String src = UpdateConfig.getApplicationPath() + File.separator + path;
                File srcF = new File(src);
                if (srcF.isDirectory()) {
                    FileUtils.deleteDirectory(srcF);
                }
                if (srcF.isFile()) {
                    srcF.delete();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            success = false;
        }
        return success;
    }

    /**
     * download files
     *
     * @return
     */
    private boolean downloadFiles() {
        List<RemoteFile> updateFiles = updateChecker.getUpdateFiles();
        int fileCount = updateFiles.size();
        for (int i = 0; i < fileCount; i++) {
            RemoteFile rf = updateFiles.get(i);
            String local = UpdateConfig.getTempDir() + File.separator + rf.getPath();

            if (rf.isUnZip() || rf.getPath().contains("..") && !rf.isNeedDelete()) {
                local = UpdateConfig.getTempDir() + File.separator
                        + rf.getUrl().substring(rf.getUrl().lastIndexOf("/") + 1);
            }

            File fn = new File(local);
            fn.getParentFile().mkdirs();
            if (updaterListener != null) {
                updaterListener.download(fn.getName(), fileCount, i + 1);
            }
            if (rf.isNeedDelete()) {
                onDlStateChange(1, 1, 1);
                continue;
            }

            DownloadFile dlFile = HttpDownloader.download(rf.getUrl(), local, this);
            if (dlFile.getState() == DownloadState.Sucess) {
                dlFile.setRemoteFile(rf);
                dlFiles.add(dlFile);
            } else {
                String msg = String.format("%s更新失败!", fn.getName());
                log.info(msg);
                if (updaterListener != null) {
                    updaterListener.updateError();
                }
                return false;
            }
        }
        return true;
    }

    /**
     * backup files
     *
     * @return
     * @throws IOException
     */
    private void backupFiles() throws IOException {

        File bkDir = new File(UpdateConfig.getBackupDir());
        FileUtils.deleteDirectory(bkDir);
        bkDir.mkdirs();
        for (DownloadFile df : dlFiles) {
            String local = df.getLocal();
            String path = local.replace(UpdateConfig.getTempDir(), "");
            String src = UpdateConfig.getApplicationPath() + File.separator + path;
            String dest = UpdateConfig.getBackupDir() + File.separator + path;
            File srcF = new File(src);
            copyFile(srcF, new File(dest));
        }
        List<RemoteFile> discardFiles = UpdateChecker.getDiscardFiles();
        for (RemoteFile rf : discardFiles) {
            String path = rf.getPath().replace(" ", "");
            String src = UpdateConfig.getApplicationPath() + File.separator + path;
            String dest = UpdateConfig.getBackupDir() + File.separator + path;
            File srcF = new File(src);
            copyFile(srcF, new File(dest));
        }
    }

    /**
     * copy files
     *
     * @return
     */
    private boolean copyFiles() {
        boolean res = true;
        try {
            for (DownloadFile df : dlFiles) {

                // no need to copy file that need unzip process.
                if (df.isUnzip()) {
                    continue;
                }

                String local = df.getLocal();
                String path = df.getPath();
                String dest = UpdateConfig.getApplicationPath() + File.separator + path;
                File srcF = new File(local);

                // first add backup file lists
                backupFiles.add(local);
//                if (StringUtils.isNotEmpty(ImageUtils.getLIBFILENAME()) && new File(ImageUtils.getLIBFILENAME()).exists()
//                        && srcF.getName().equals(new File(ImageUtils.getLIBFILENAME()).getName())) {
//                    log.info(new File(ImageUtils.getLIBFILENAME()).getName() + " to unload");
//                    BizUtils.freeDll(new File(ImageUtils.getLIBFILENAME()).getName());
//                }
                copyFile(srcF, new File(dest));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            res = false;
        }

        return res;
    }

    /**
     * rollback files
     *
     * @return
     * @throws IOException
     */
    private void rollbackFiles() throws IOException {
        for (String str : backupFiles) {
            String src = str;
            String path = src.replace(UpdateConfig.getTempDir(), "");
            String dest = UpdateConfig.getApplicationPath() + File.separator + path;
            File srcF = new File(src);
            copyFile(srcF, new File(dest));
        }
    }

    /**
     * update mClient.dat
     */
    private void updateLocalXml() throws IOException {
        RemoteXml remoteXml = updateChecker.getRemoteXml();

        StringBuffer xml = new StringBuffer();
        xml.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
        xml.append(
                "<Config xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\r\n");
        xml.append("<Enabled>true</Enabled>\r\n");

        xml.append(String.format("<ServerUrl>%s</ServerUrl>\r\n", UpdateConfig.getServerUrl()));

        xml.append(String.format("<publishTime>%s</publishTime>\r\n", remoteXml.getPublishTime()));

        xml.append(String.format("<version>%s</version>\r\n", remoteXml.getVersion()));

        xml.append(String.format("<PublishInfo>%s</PublishInfo>\r\n", remoteXml.getPublishInfo()));

        xml.append("<UpdateFileList>\r\n");
        for (RemoteFile rf : remoteXml.getRemoteFiles()) {

            String str = "<LocalFile path=\"%s\" lastver=\"%s\" size=\"%s\" />\r\n";
            str = String.format(str, rf.getPath(), rf.getLastver(), rf.getSize());
            xml.append(str);

        }
        xml.append("</UpdateFileList>\r\n");
        xml.append("</Config>\r\n");
        saveLocalXml(xml.toString());
    }

    /**
     * save mClient.dat
     *
     * @param xml
     * @throws IOException
     */
    private void saveLocalXml(String xml) throws IOException {
        String localXmlPath = UpdateConfig.getClientDat();
        File f = new File(localXmlPath);
        if (!f.exists()) {
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            f.createNewFile();
        }
        byte[] buff = xml.getBytes("utf-8");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(buff);
            fos.flush();
            fos.close();
            fos = null;
        } finally {
            if (fos != null) {
                fos.close();
            }
        }

    }

    /**
     * copy file
     *
     * @param srcF
     * @param destFile
     */
    public void copyFile(File srcF, File destFile) {
        try {

            if (srcF.exists()) {
                if (srcF.isFile()) {
                    FileUtils.copyFile(srcF, destFile);
                }
                if (srcF.isDirectory()) {
                    FileUtils.copyDirectory(srcF, destFile);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void onDlStateChange(int state, int length, int transferred) {
        if (updaterListener != null) {
            updaterListener.transferred(length, transferred);
        }
    }
}

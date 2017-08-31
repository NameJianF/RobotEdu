package robot.client.updater;

/**
 * 下载实体类
 * 
 * @author Administrator
 */
public class DownloadFile {
	private String url;
	private int statusCode;
	private String local;
	private String md5;
	private RemoteFile remoteFile;

	/**
	 * 下载状态
	 */
	private int state;

	private String errorMsg;
	private String stacktrace;

	public DownloadFile() {

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}

	public RemoteFile getRemoteFile() {
		return remoteFile;
	}

	public void setRemoteFile(RemoteFile remoteFile) {
		this.remoteFile = remoteFile;
	}

	public boolean isUnzip() {
		if(this.getRemoteFile() != null){
			return this.getRemoteFile().isUnZip();
		}
		return false;
	}
	
	/**
	 * Where should this file put/unzip.
	 * @return
	 */
	public String getPath(){
		if(this.getRemoteFile() != null){
			return this.getRemoteFile().getPath();
		}
		return "";
	}
}

package robot.client.updater;

public class RemoteFile {

	private String lastver;
	private String path;
	private boolean needUnZip;
	private String url;
	private int size;
	private boolean needRestart;
	private boolean needDiscard;

	public boolean isNeedDelete() {
		return needDiscard;
	}

	public void setNeedDelete(boolean needDiscard) {
		this.needDiscard = needDiscard;
	}

	public String getLastver() {
		return lastver;
	}

	public void setLastver(String lastver) {
		this.lastver = lastver;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isNeedRestart() {
		return needRestart;
	}

	public void setNeedRestart(boolean needRestart) {
		this.needRestart = needRestart;
	}

	public boolean isUnZip() {
		return needUnZip;
	}

	public void setNeedUnZip(boolean needUnZip) {
		this.needUnZip = needUnZip;
	}

}

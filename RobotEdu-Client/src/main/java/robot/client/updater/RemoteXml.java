package robot.client.updater;

import java.util.List;

public class RemoteXml {
	private String publishInfo;

	private String publishTime;

	private String version;

	private List<RemoteFile> remoteFiles;

	public String getPublishInfo() {
		return publishInfo;
	}

	public void setPublishInfo(String publishInfo) {
		this.publishInfo = publishInfo;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<RemoteFile> getRemoteFiles() {
		return remoteFiles;
	}

	public void setRemoteFiles(List<RemoteFile> remoteFiles) {
		this.remoteFiles = remoteFiles;
	}

}

package robot.client.updater;

import java.util.List;

//<Config xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema">
//<Enabled>true</Enabled>
//<ServerUrl>http://res.testin.cn/iTestin/5/mClient.xml</ServerUrl>
//<publishTime>2014年2月18号</publishTime>
//<version>v3.3.65</version>
//<PublishInfo />
//<UpdateFileList>
//<LocalFile path="tools/script/com.hugenstar.tdzmclientprev.test.apk" lastver="3.0.1.13" size="94760" />
//</UpdateFileList>
//</Config>
public class LocalXml {

	private boolean enabled;

	private String serverUrl;

	private String publishTime;

	private String version;

	private String publishInfo;

	private List<LocalFile> localFiles;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
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

	public String getPublishInfo() {
		return publishInfo;
	}

	public void setPublishInfo(String publishInfo) {
		this.publishInfo = publishInfo;
	}

	public List<LocalFile> getLocalFiles() {
		return localFiles;
	}

	public void setLocalFiles(List<LocalFile> localFiles) {
		this.localFiles = localFiles;
	}

}

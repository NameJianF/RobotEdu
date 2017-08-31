package robot.client.updater;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UpdateChecker implements OnDownloadStateChangeListener {

	static Logger log = Logger.getLogger(UpdateChecker.class);
	private String remoteXmlPath;
	private RemoteXml remoteXml;

	private String localXmlPath;
	private LocalXml localXml;
	private List<RemoteFile> updateFiles;
	private static List<RemoteFile> discardFiles;
	private static List<RemoteFile> zipFiles;
	private static String localXmlPublish;

	public UpdateChecker() {
		remoteXmlPath = UpdateConfig.getTempDir() //
				+ File.separator //
				+ "mClient.xml";
		localXmlPath = UpdateConfig.getClientDat();

		remoteXml = new RemoteXml();
		localXml = new LocalXml();

		updateFiles = new ArrayList<RemoteFile>();
	}

	public String getPublishInfo() {
		return localXmlPublish;
	}

	public String getRemoteXmlPath() {
		return remoteXmlPath;
	}

	public static List<RemoteFile> getDiscardFiles() {
		return discardFiles;
	}

	public RemoteXml getRemoteXml() {
		return remoteXml;
	}

	public String getLocalXmlPath() {
		return localXmlPath;
	}

	public LocalXml getLocalXml() {
		return localXml;
	}

	public List<RemoteFile> getUpdateFiles() {
		return updateFiles;
	}

	public static List<RemoteFile> getZipFiles() {
		return zipFiles;
	}

	/**
	 * is need update
	 * 
	 * @return
	 */
	public boolean isNeedUpdate() {
		try {
			log.info("Check if updates are available.");
			File tmpDir = new File(UpdateConfig.getTempDir());
			FileUtils.deleteDirectory(tmpDir);
			tmpDir.mkdirs();
			// clear log
			File logf = new File(UpdateConfig.getUpdateLog());
			logf.delete();
			DownloadFile dlFile = downloadRemoteXml();
			if (dlFile.getState() == DownloadState.Sucess) {
				parseRemoteXml();
				parseLocalXml();
				parseUpdateFile();
				parseDiscardFile();
				parseZipFiles();
			} else {
				log.error("检查更新失败!");
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		} finally {
		}
		return updateFiles.size() > 0;
	}

	/**
	 * 下载 mClient.xml download mClient.xml
	 * 
	 * @return
	 */
	private DownloadFile downloadRemoteXml() {
		log.info(String.format("Download remote xml %s", remoteXmlPath));
		DownloadFile dlFile = HttpDownloader.download(UpdateConfig.getServerUrl(), remoteXmlPath, this);
		log.info(String.format("Download remote xml finished. Status %d", dlFile.getStatusCode()));
		return dlFile;
	}

	/**
	 * 解析 xml parse RemoteXml
	 * 
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public void parseRemoteXml() throws ParserConfigurationException, SAXException, IOException {
		File xmlPath = new File(remoteXmlPath);
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		// get xml object
		Document doc = builder.parse(xmlPath);
		Element root = doc.getDocumentElement();
		NodeList el = root.getElementsByTagName("version");
		if (el.getLength() > 0) {
			Node node = el.item(0);
			String value = node.getTextContent();
			if (value != null) {
				remoteXml.setVersion(value.trim());
			}

		}
		el = root.getElementsByTagName("publishInfo");
		if (el.getLength() > 0) {
			Node node = el.item(0);
			String value = node.getTextContent();
			if (value != null) {
				remoteXml.setPublishInfo(value.trim());
			}
		}

		el = root.getElementsByTagName("publishTime");
		if (el.getLength() > 0) {
			Node node = el.item(0);
			String value = node.getTextContent();
			if (value != null) {
				remoteXml.setPublishTime(value.trim());
			}
		}

		List<RemoteFile> remoteFiles = new ArrayList<RemoteFile>();
		remoteXml.setRemoteFiles(remoteFiles);
		NodeList el_files = root.getElementsByTagName("file");
		for (int i = 0; i < el_files.getLength(); i++) {
			RemoteFile remoteFile = new RemoteFile();
			remoteFiles.add(remoteFile);

			Node fileNode = el_files.item(i);
			NamedNodeMap attrs = fileNode.getAttributes();
			for (int j = 0; j < attrs.getLength(); j++) {
				Node node = attrs.item(j);
				String key = node.getNodeName();
				if (key != null) {
					if (key.equals("lastver")) {
						String value = node.getNodeValue();
						if (value != null) {
							remoteFile.setLastver(value.trim());
						}
					} else if (key.equals("path")) {
						String value = node.getNodeValue();
						if (value != null) {
							remoteFile.setPath(value.trim());
						}
					} else if (key.equals("url")) {
						String value = node.getNodeValue();
						if (value != null) {
							remoteFile.setUrl(value.trim());
						}
					} else if (key.equals("size")) {
						try {
							String value = node.getNodeValue();
							if (value != null) {
								int size = Integer.parseInt(value.trim());
								remoteFile.setSize(size);
							}
						} catch (Exception ex) {
							log.error(ex.getMessage(), ex);
						}

					} else if (key.equals("needRestart")) {
						try {
							String value = node.getNodeValue();
							if (value != null) {
								boolean needRestart = Boolean.parseBoolean(value.trim());
								remoteFile.setNeedRestart(needRestart);
							}
						} catch (Exception ex) {
							log.error(ex.getMessage(), ex);
						}

					} else if (key.equals("delete")) {
						try {
							String value = node.getNodeValue();
							if (value != null) {
								boolean needDiscard = Boolean.parseBoolean(value.trim());
								remoteFile.setNeedDelete(needDiscard);
							}
						} catch (Exception ex) {
							log.error(ex.getMessage(), ex);
						}
					} else if (key.equals("unzip")) {
						try {
							String value = node.getNodeValue();
							if (value != null) {
								boolean needUnZip = Boolean.parseBoolean(value.trim());
								remoteFile.setNeedUnZip(needUnZip);
							}
						} catch (Exception ex) {
							log.error(ex.getMessage(), ex);
						}
					}
				}
			}
		}
	}

	public String getRemotePublishInfo() {
		try {
			return this.remoteXml.getPublishInfo();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * parse localXml
	 * 
	 * @return
	 */
	public void parseLocalXml() {
		try {
			File f = new File(localXmlPath);
			if (!f.exists()) {
				// do nothing
			} else {
				File xmlPath = new File(localXmlPath);
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = dbf.newDocumentBuilder();

				Document doc = builder.parse(xmlPath);
				Element root = doc.getDocumentElement();

				NodeList el = root.getElementsByTagName("Enabled");
				try {
					if (el.getLength() > 0) {
						Node node = el.item(0);
						String value = node.getTextContent();
						if (value != null) {
							localXml.setEnabled(Boolean.parseBoolean(value.trim()));
						}

					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}

				el = root.getElementsByTagName("ServerUrl");
				if (el.getLength() > 0) {
					Node node = el.item(0);
					String value = node.getTextContent();
					if (value != null) {
						localXml.setServerUrl(value.trim());
					}
				}

				el = root.getElementsByTagName("version");
				if (el.getLength() > 0) {
					Node node = el.item(0);
					String value = node.getTextContent();
					if (value != null) {
						localXml.setVersion(value.trim());
					}

				}

				el = root.getElementsByTagName("PublishInfo");
				if (el.getLength() > 0) {
					Node node = el.item(0);
					String value = node.getTextContent();
					if (value != null) {
						localXml.setPublishInfo(value.trim());
						localXmlPublish = value.trim();
					}
				}

				el = root.getElementsByTagName("publishTime");
				if (el.getLength() > 0) {
					Node node = el.item(0);
					String value = node.getTextContent();
					if (value != null) {
						localXml.setPublishTime(value.trim());
					}
				}

				List<LocalFile> localFiles = new ArrayList<LocalFile>();
				localXml.setLocalFiles(localFiles);

				NodeList el_files = root.getElementsByTagName("LocalFile");
				for (int i = 0; i < el_files.getLength(); i++) {
					LocalFile localFile = new LocalFile();
					localFiles.add(localFile);

					Node fileNode = el_files.item(i);

					NamedNodeMap attrs = fileNode.getAttributes();
					for (int j = 0; j < attrs.getLength(); j++) {
						Node node = attrs.item(j);
						String key = node.getNodeName();
						if (key != null) {
							if (key.equals("lastver")) {
								String value = node.getNodeValue();
								if (value != null) {
									localFile.setLastver(value.trim());
								}
							} else if (key.equals("path")) {
								String value = node.getNodeValue();
								if (value != null) {
									localFile.setPath(value.trim());
								}
							} else if (key.equals("size")) {
								try {
									String value = node.getNodeValue();
									if (value != null) {
										int size = Integer.parseInt(value.trim());
										localFile.setSize(size);
									}
								} catch (Exception e) {
									log.error(e.getMessage(), e);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	/**
	 * parse update file
	 * 
	 * @return
	 */
	private void parseUpdateFile() {
		List<RemoteFile> remoteFiles = remoteXml.getRemoteFiles();
		List<LocalFile> localFiles = localXml.getLocalFiles();
		if (remoteFiles != null) {
			for (RemoteFile rf : remoteFiles) {
				LocalFile lfcfg = null;
				if (localFiles != null) {
					for (LocalFile lf : localFiles) {
						if (rf.getPath().equals(lf.getPath())) {
							lfcfg = lf;
							break;
						}
					}
				}
				if (lfcfg == null) {
					updateFiles.add(rf);
				} else if (compareVersion(rf.getLastver(), lfcfg.getLastver()) > 0) {
					updateFiles.add(rf);
				}
			}
		}

	}

	/**
	 * 分析需要删除的文件
	 * 
	 * @return
	 */
	private List<RemoteFile> parseDiscardFile() {

		discardFiles = new ArrayList<RemoteFile>();
		List<RemoteFile> remoteFiles = remoteXml.getRemoteFiles();

		if (remoteFiles != null) {
			for (RemoteFile rf : remoteFiles) {

				if (rf.isNeedDelete()) {
					discardFiles.add(rf);
				}
			}
		}
		return discardFiles;
	}

	/**
	 * 分析要解压的文件
	 * 
	 * @return
	 */
	private List<RemoteFile> parseZipFiles() {
		zipFiles = new ArrayList<RemoteFile>();
		List<RemoteFile> remoteFiles = remoteXml.getRemoteFiles();
		if (remoteFiles != null) {
			for (RemoteFile rf : remoteFiles) {
				if (rf.isUnZip()) {
					zipFiles.add(rf);
				}
			}
		}
		return zipFiles;

	}

	/**
	 * compare version (0 v1 = v2) (1 v1>v2) (-1 v1<v2)
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	private int compareVersion(String v1, String v2) {
		String[] var1 = v1.split("\\.");
		String[] var2 = v2.split("\\.");
		int len1 = var1.length;
		// 补位
		for (int i = len1; i < 10; i++) {
			String[] dest = new String[var1.length + 1];
			System.arraycopy(var1, 0, dest, 0, var1.length);
			dest[var1.length] = "0";
			var1 = dest;
		}

		int len2 = var2.length;
		for (int i = len2; i < 10; i++) {
			String[] dest = new String[var2.length + 1];
			System.arraycopy(var2, 0, dest, 0, var2.length);
			dest[var2.length] = "0";
			var2 = dest;
		}

		for (int i = 0; i < var1.length; i++) {
			int num1 = Integer.parseInt(var1[i]);
			int num2 = Integer.parseInt(var2[i]);
			if (num1 > num2) {
				return 1;
			} else if (num1 < num2) {
				return -1;
			} else if (num1 == num2) {
				continue;
			}
		}
		return 0;
	}

	@Override
	public void onDlStateChange(int state, int length, int transferred) {
	}

}

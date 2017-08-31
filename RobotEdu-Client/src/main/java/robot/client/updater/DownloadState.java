package robot.client.updater;

/**
 * 下载状态类
 * 
 * @author Administrator
 * 
 */
public class DownloadState {

	/**
	 * 未开始
	 */
	public static final int NotStarted = 0;

	/**
	 * 下载成功
	 */
	public static final int Sucess = 2;
	/**
	 * 正在下载中
	 */
	public static final int Downloading = 1;
	/**
	 * 下载异常出现错误
	 */
	public static final int Error = -1;
	/**
	 * 下载地址有误非程序错误
	 */
	public static final int Failure = -2;
	
	/**
	 * 下载超时
	 */
	public static final int Timeout = 9;
}

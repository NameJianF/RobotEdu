package robot.client.updater;


/***
 * 状态变化监听器
 * 
 * @author Administrator
 * 
 */
public interface OnDownloadStateChangeListener {
	/**
	 * 下载状态变更时提示
	 * 
	 * @param state
	 *            0 下载完成、1 下载中、-2 下载失败 、-1下载错误
	 * @param length
	 *            要下载的文件总大小(b)
	 * @param transferred
	 *            已下载大小(b)
	 */
	void onDlStateChange(int state, int length, int transferred);
}

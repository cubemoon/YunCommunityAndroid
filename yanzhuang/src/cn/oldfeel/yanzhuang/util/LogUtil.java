package cn.oldfeel.yanzhuang.util;

import android.util.Log;
import cn.oldfeel.yanzhuang.app.Constant;

/**
 * 打印日志工具类
 * 
 * @author oldfeel
 * 
 *         Created on: 2014-1-10
 */
public class LogUtil {
	/**
	 * 打印日志
	 * 
	 * @param log
	 */
	public static void showLog(String log) {
		if (Constant.IS_DEBUG) {
			Log.d("example", log);
		}
	}
}

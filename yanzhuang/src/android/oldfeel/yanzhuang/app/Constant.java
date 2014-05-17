package android.oldfeel.yanzhuang.app;

import android.os.Environment;

/**
 * 项目中用到的一些常量
 * 
 * @author oldfeel
 * 
 *         Created on: 2014-1-10
 */
public class Constant {
	/** 接口根目录 */
	public static final String ROOT_URL = "http://192.168.1.103:8080/oldfeel_web/json";// 我的电脑
	// public static final String ROOT_URL =
	// "http://192.168.1.111:8080/oldfeel_web/json";//媳妇的电脑
	/** 本应用的根目录 */
	public static final String ROOT_DIR = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/yanzhuang/";
	/** 数据库名字 */
	public static final String DB_NAME = ROOT_DIR + "yanzhuang.db";
	/** SharedPreferences用到的应用名称 */
	public static final String APP_NAME = "yanzhuang";
	/** 拍照后图片的临时文件夹 */
	public final static String FILE_SAVEPATH = ROOT_DIR + "image";
	/** 视频保存目录 */
	public static final String VIDEO_DIR = ROOT_DIR + "video";
	/** true为正在调试,可以打印log日志等... */
	public static final boolean IS_DEBUG = true;
	/** 每页信息数量 */
	public static final long PAGE_SIZE = 10;
	/** 活动 */
	public static final int TYPE_ACTIVITY = 1;
	/** 商家服务 */
	public static final int TYPE_BUSINESS = 2;
	/** 个人服务 */
	public static final int TYPE_PERSONAL = 3;
}

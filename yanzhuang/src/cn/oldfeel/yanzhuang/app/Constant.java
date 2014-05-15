package cn.oldfeel.yanzhuang.app;

import android.os.Environment;

/**
 * 项目中用到的一些常量
 * 
 * @author oldfeel
 * 
 *         Created on: 2014-1-10
 */
public class Constant {
	/** 本应用的根目录 */
	public static final String ROOT_DIR = Environment
			.getExternalStorageDirectory().getAbsolutePath() + "/meow/";
	/** 数据库名字 */
	public static final String DB_NAME = ROOT_DIR + "meow.db";
	/** SharedPreferences用到的应用名称 */
	public static final String APP_NAME = "meow";
	/** 拍照后图片的临时文件夹 */
	public final static String FILE_SAVEPATH = ROOT_DIR + "Portrait";
	/** 视频保存目录 */
	public static final String VIDEO_DIR = ROOT_DIR + "video";
	/** 系统下载目录 */
	public static final String SYSTEM_DOWNLOAD_DIR = "meow/download";
	/** true为正在调试,可以打印log日志等... */
	public static final boolean IS_DEBUG = true;
	/** 每页视频数量 */
	public static final long PAGE_SIZE = 10;
	/** 录制时间,8秒 */
	public static final int RECORD_TIME = 8 * 1000;
	/** 评论成功 */
	public static final String ACTION_COMMENT_SUCCESS = "meow.action.comment.success";
}

package com.yuncommunity.theme.android.app;

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
	public static final String ROOT_URL = "http://192.168.0.104:8080/v1/client/";// 我的电脑
	// public static final String ROOT_URL =
	// "http://yuncommunity.com/v1/client/";// 服务器
	/** SharedPreferences用到的应用名称 */
	public static final String APP_NAME = "yuncommunity";
	/** 加密解密用到的key */
	public static final String KEY = "yuncommu";
	/** 本应用的根目录 */
	public static final String ROOT_DIR = Environment
			.getExternalStorageDirectory().getAbsolutePath()
			+ "/"
			+ APP_NAME
			+ "/";
	/** 数据库名字 */
	public static final String DB_NAME = APP_NAME + ".db";
	/** 数据库版本 */
	public static final int DB_VERSION = 1;
	/** 拍照后图片的临时文件夹 */
	public final static String FILE_SAVEPATH = ROOT_DIR + "media";
	/** true为正在调试,可以打印log日志等... */
	public static final boolean IS_DEBUG = true;
	/** 每页中信息数量 */
	public static final int PAGE_SIZE = 20;
	/** 活动 */
	public static final int TYPE_ACTIVITY = 1;
	/** 商家服务 */
	public static final int TYPE_BUSINESS = 2;
	/** 任务 */
	public static final int TYPE_TASK = 3;
	/** 关注用户 */
	public static final int TYPE_FOLLOWING_USER = 4;
	/** 评论 */
	public static final int TYPE_COMMENT = 5;
	/** 赞同评论 */
	public static final int TYPE_APPROVAL = 6;
	/** 反对评论 */
	public static final int TYPE_OPPOSITION = 7;
	/** 广场微博 */
	public static final int TYPE_SQUARE = 8;

}

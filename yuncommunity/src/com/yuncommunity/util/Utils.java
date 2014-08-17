package com.yuncommunity.util;

import java.util.Locale;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 常用工具类
 * 
 * @author oldfeel
 * 
 *         Create on: 2014年7月27日
 */
public class Utils {
	/**
	 * 打电话
	 * 
	 * @param context
	 * @param phone
	 */
	public static void call(Context context, String phone) {
		Intent intent = new Intent("android.intent.action.CALL",
				Uri.parse("tel:" + phone));
		context.startActivity(intent);
	}

	/**
	 * 
	 * @return true为中国语言,false为不是中国语言
	 */
	public static boolean isChinese() {
		return Locale.getDefault() == Locale.CHINA
				|| Locale.getDefault() == Locale.CHINESE;
	}

}

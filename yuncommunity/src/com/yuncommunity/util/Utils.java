package com.yuncommunity.util;

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

}

package android.oldfeel.yanzhuang.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * 配置信息工具类
 * 
 * @author oldfeel
 * 
 */
public class PreferenceUtil {

	public static boolean getBoolean(Context context, int keyid) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sp.getBoolean(context.getString(keyid), false);
	}

}

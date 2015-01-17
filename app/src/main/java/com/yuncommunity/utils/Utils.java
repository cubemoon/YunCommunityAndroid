package com.yuncommunity.utils;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.oldfeel.interfaces.MyLocationListener;
import com.oldfeel.utils.DialogUtil;
import com.yuncommunity.R;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.ios.I_LoginActivity;

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
		String def = Locale.getDefault().toString();
		String china = Locale.CHINA.toString();
		String chinese = Locale.CHINESE.toString();
		return def.equals(china) || def.equals(chinese);
	}

	/**
	 * 开始定位
	 * 
	 * @param activity
	 * @param myLocationListener
	 */
	public static void startLocation(Activity activity,
			MyLocationListener myLocationListener) {
		if (isChinese()) {
			startLocationBaidu(activity, myLocationListener);
		} else {
			startLocationGoogle(activity, myLocationListener);
		}

	}

	/**
	 * 谷歌定位
	 * 
	 * @param activity
	 * @param myLocationListener
	 */
	private static void startLocationGoogle(Activity activity,
			MyLocationListener myLocationListener) {
	}

	/**
	 * 百度定位
	 * 
	 * @param activity
	 * @param myLocationListener
	 */
	private static void startLocationBaidu(final Activity activity,
			final MyLocationListener myLocationListener) {
		final LocationClient myLocationClient = new LocationClient(activity);
		myLocationClient.registerLocationListener(new BDLocationListener() {

			@Override
			public void onReceiveLocation(BDLocation location) {
				myLocationListener.location(location.getLatitude(),
						location.getLongitude());
				myLocationClient.stop();
				DialogUtil.getInstance().cancelPd();
				DialogUtil.getInstance().showToast(activity, "定位成功");
			}
		});
		myLocationClient.start();
		DialogUtil.getInstance().showPd(activity, "正在获取位置信息...");
	}

	/**
	 * 
	 * @param activity
	 * @param isGoLogin
	 *            true为显示是否登录的对话框,false为不显示
	 * @return
	 */
	public static boolean isLogin(final Activity activity, boolean isGoLogin) {
		boolean isLogin = false;
		if (LoginInfo.getInstance(activity).getUserId() != 0) {
			isLogin = true;
		}
		if (!isLogin && isGoLogin) {
			new AlertDialog.Builder(activity)
					.setTitle("您需要登录后才能继续操作")
					.setPositiveButton("登录/注册",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									activity.startActivity(getThemeIntent(
											activity, I_LoginActivity.class));
								}
							}).setNegativeButton("取消", null).show();
		}
		return isLogin;

	}

	/**
	 * 判断用户是否登录
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isLogin(final Activity activity) {
		return isLogin(activity, true);
	}

	/**
	 * 根据className获取主题对应的intent
	 * 
	 * @param activity
	 * @return
	 */
	public static Intent getThemeIntent(Activity fragmentActivity,
			Class<?> activity) {
		Intent intent = new Intent();
		String name = "";
		String simpleName = activity.getSimpleName();

		LoginInfo info = LoginInfo.getInstance(fragmentActivity);
		String theme = info.getTheme();
		if (theme.equals(fragmentActivity.getString(R.string.theme_android))) {
			name = getThemeName(simpleName, "com.yuncommunity.theme.android.",
					"A_");
		} else if (theme.equals(fragmentActivity.getString(R.string.theme_ios))) {
			name = getThemeName(simpleName, "com.yuncommunity.theme.ios.", "I_");
		}
		intent.setClassName(fragmentActivity, name);
		return intent;
	}

	/**
	 * 
	 * @param packageString
	 *            包名
	 * @param simpleName
	 *            不带包名的类名
	 * @param prefix
	 *            前缀
	 * @return
	 */
	private static String getThemeName(String simpleName, String packageString,
			String prefix) {
		if (!simpleName.startsWith(prefix)) {
			int index = simpleName.indexOf("_");
			if (index > -1 && index < 3) {
				simpleName = simpleName.substring(index + 1,
						simpleName.length());
			}
			simpleName = prefix + simpleName;
		}
		return packageString + simpleName;
	}
}

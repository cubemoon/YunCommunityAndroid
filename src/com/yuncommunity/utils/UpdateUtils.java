package com.yuncommunity.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import com.google.gson.Gson;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;
import com.yuncommunity.theme.ios.I_LoginActivity;

/**
 * @author oldfeel
 * 
 *         Create on: 2014年11月13日
 */
public class UpdateUtils {

	public static void update(Activity activity) {
		update(activity, "", null);
	}

	/**
	 * 更新用户信息
	 * 
	 * @param activity
	 * @param text
	 * @param stringListener
	 */
	public static void update(Activity activity, String text,
			RequestStringListener stringListener) {
		LoginInfo loginInfo = LoginInfo.getInstance(activity);
		if (loginInfo.getUserId() == 0) {
			return;
		}
		loginInfo.getUserInfo().setPassword(loginInfo.getRealPassword());
		NetUtil netUtil = new NetUtil(activity, JsonApi.UPDATE_USER_INFO);
		netUtil.setParams("userinfo",
				new Gson().toJson(loginInfo.getUserInfo()));
		netUtil.postRequest(text, stringListener);
	}

	public static boolean isLogin(final Activity activity) {
		boolean isLogin = false;
		if (LoginInfo.getInstance(activity).getUserId() != 0) {
			isLogin = true;
		}
		if (!isLogin) {
			new AlertDialog.Builder(activity)
					.setTitle("您需要登录后才能继续操作")
					.setPositiveButton("登录/注册",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									activity.startActivity(new Intent(activity,
											I_LoginActivity.class));
								}
							}).setNegativeButton("取消", null).show();
		}
		return isLogin;
	}
}

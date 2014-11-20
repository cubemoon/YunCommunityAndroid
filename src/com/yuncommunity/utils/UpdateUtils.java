package com.yuncommunity.utils;

import android.app.Activity;

import com.google.gson.Gson;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.yuncommunity.conf.JsonApi;
import com.yuncommunity.conf.LoginInfo;

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
}

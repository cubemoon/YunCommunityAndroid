package com.yuncommunity.conf;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.oldfeel.utils.DesUtil;
import com.oldfeel.utils.NetUtil;
import com.oldfeel.utils.NetUtil.RequestStringListener;
import com.oldfeel.utils.StringUtil;
import com.yuncommunity.R;
import com.yuncommunity.item.CommunityItem;
import com.yuncommunity.item.UserItem;
import com.yuncommunity.theme.android.MainActivity;
import com.yuncommunity.theme.ios.IMainActivity;

/**
 * 个人信息
 * 
 * @author oldfeel
 * 
 *         Created on: 2014年3月1日
 */
public class LoginInfo {
	private static LoginInfo loginInfo;
	private SharedPreferences sp;
	private Editor editor;
	private UserItem userInfo; // 登陆用户信息
	private Context context;

	public static LoginInfo getInstance(Context context) {
		if (loginInfo == null) {
			loginInfo = new LoginInfo(context);
		}
		return loginInfo;
	}

	public LoginInfo(Context context) {
		this.context = context;
		sp = context.getSharedPreferences(Constant.APP_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();
		editor.commit();
		saveInfo(DesUtil.decode(Constant.KEY, sp.getString("logininfo", "")));
	}

	public boolean isLogin() {
		if (userInfo == null) {
			return false;
		}
		return userInfo.getUserid() != 0;
	}

	public void saveInfo(String result) {
		editor.putString("logininfo",
				DesUtil.encode(Constant.KEY, result.toString()));
		editor.commit();
		if (StringUtil.isEmpty(result)) {
			return;
		}
		userInfo = new Gson().fromJson(result, UserItem.class);
	}

	public void saveRealPassword(String password) {
		editor.putString("password", DesUtil.encode(Constant.KEY, password));
		editor.commit();
	}

	public String getRealPassword() {
		return DesUtil.decode(Constant.KEY, sp.getString("password", ""));
	}

	public static void update(Activity activity, String text,
			RequestStringListener stringListener) {
		LoginInfo loginInfo = LoginInfo.getInstance(activity);
		NetUtil netUtil = new NetUtil(activity, JsonApi.UPDATE_USER_INFO);
		netUtil.setParams("userid", loginInfo.getUserInfo().getUserid());
		netUtil.setParams("name", loginInfo.getUserInfo().getName());
		netUtil.setParams("password", loginInfo.getRealPassword());
		netUtil.setParams("phone", loginInfo.getUserInfo().getPhone());
		netUtil.setParams("housenumber", loginInfo.getUserInfo()
				.getHousenumber());
		netUtil.setParams("birthday", loginInfo.getUserInfo().getBirthday());
		netUtil.setParams("permission", loginInfo.getUserInfo().getPermission());
		netUtil.setParams("background",
				StringUtil.getFileName(loginInfo.getUserInfo().getBackground()));
		netUtil.setParams("avatar",
				StringUtil.getFileName(loginInfo.getUserInfo().getAvatar()));
		netUtil.setParams("friendmsg", loginInfo.getUserInfo().getFriendmsg());
		netUtil.setParams("activitymsg", loginInfo.getUserInfo()
				.getActivitymsg());
		netUtil.setParams("businessmsg", loginInfo.getUserInfo()
				.getBusinessmsg());
		netUtil.setParams("introduction", loginInfo.getUserInfo()
				.getIntroduction());
		netUtil.postRequest(text, stringListener);
	}

	public static void update(Activity activity) {
		update(activity, "", null);
	}

	public void close() {
		loginInfo = null;
	}

	public UserItem getUserInfo() {
		if (userInfo == null) {
			return new UserItem();
		}
		return userInfo;
	}

	public void setUserInfo(UserItem userInfo) {
		this.userInfo = userInfo;
	}

	public CommunityItem getCommunityInfo() {
		return getUserInfo().getCommunityInfo();
	}

	public long getUserId() {
		return getUserInfo().getUserid();
	}

	public String getTheme() {
		return sp.getString("theme", context.getString(R.string.theme_ios));
	}

	public void setTheme(String theme) {
		editor.putString("theme", theme);
		editor.commit();
	}

	public String getName() {
		if (userInfo != null) {
			return userInfo.getName();
		}
		return context.getString(R.string.login_or_register);
	}

	/**
	 * 获取当前用户使用的主题风格
	 * 
	 * @return
	 */
	public Class<?> getThemeClass() {
		String name = getTheme();
		if (name.equals(context.getString(R.string.theme_android))) {
			return MainActivity.class;
		} else if (name.equals(context.getString(R.string.theme_ios))) {
			return IMainActivity.class;
		}
		return IMainActivity.class;
	}

}

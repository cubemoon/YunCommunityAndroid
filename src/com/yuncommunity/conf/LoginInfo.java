package com.yuncommunity.conf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.yuncommunity.theme.ios.I_LoginActivity;
import com.yuncommunity.theme.ios.I_MainActivity;

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

	public static void update(Activity activity) {
		update(activity, "", null);
	}

	public static void update(Activity activity, String text,
			RequestStringListener stringListener) {
		LoginInfo loginInfo = LoginInfo.getInstance(activity);
		if (loginInfo.getUserId() == 0) {
			return;
		}
		NetUtil netUtil = new NetUtil(activity, JsonApi.UPDATE_USER_INFO);
		netUtil.setParams("userinfo",
				new Gson().toJson(loginInfo.getUserInfo()));
		netUtil.postRequest(text, stringListener);
	}

	public boolean isLogin(final Activity activity) {
		boolean isLogin = false;
		if (userInfo != null && userInfo.getUserid() != 0) {
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
									activity.startActivity(new Intent(context,
											I_LoginActivity.class));
								}
							}).setNegativeButton("取消", null).show();
		}
		return isLogin;
	}

	public void saveInfo(String result) {
		UserItem item = new Gson().fromJson(result, UserItem.class);
		if (getCommunityInfo().getCommunityid() != 0) { // 如果用户是第一次注册/登录,可能没有小区,所以要为用户赋值小区信息
			item.setCommunityInfo(getCommunityInfo());
		}
		editor.putString("logininfo",
				DesUtil.encode(Constant.KEY, new Gson().toJson(item)));
		editor.commit();
		if (StringUtil.isEmpty(result)) {
			return;
		}
		userInfo = new Gson().fromJson(result, UserItem.class);
	}

	public UserItem getUserInfo() {
		if (userInfo == null) {
			return new UserItem();
		}
		return userInfo;
	}

	public void saveRealPassword(String password) {
		editor.putString("password", DesUtil.encode(Constant.KEY, password));
		editor.commit();
	}

	public String getRealPassword() {
		return DesUtil.decode(Constant.KEY, sp.getString("password", ""));
	}

	public void close() {
		loginInfo = null;
	}

	/**
	 * 保存小区信息
	 * 
	 * @param communityItem
	 */
	public void saveCommunityInfo(CommunityItem communityItem) {
		getUserInfo().setCommunityInfo(communityItem);
		editor.putString("communityitem", new Gson().toJson(communityItem));
		editor.commit();
	}

	public CommunityItem getCommunityInfo() {
		if (getUserInfo().getCommunityInfo().getCommunityid() == 0) {
			String temp = sp.getString("communityitem", null);
			if (temp != null) {
				return new Gson().fromJson(temp, CommunityItem.class);
			}
		}
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
		String theme = getTheme();
		if (theme.equals(context.getString(R.string.theme_android))) {
			return MainActivity.class;
		} else if (theme.equals(context.getString(R.string.theme_ios))) {
			return I_MainActivity.class;
		}
		return I_MainActivity.class;
	}

}

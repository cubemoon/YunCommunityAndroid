package com.yuncommunity.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.yuncommunity.item.UserItem;
import com.yuncommunity.util.DesUtil;
import com.yuncommunity.util.NetUtil;
import com.yuncommunity.util.NetUtil.RequestStringListener;
import com.yuncommunity.util.StringUtil;

/**
 * 个人信息
 * 
 * @author oldfeel
 * 
 *         Created on: 2014年3月1日
 */
public class LoginInfo extends UserItem {
	private static LoginInfo loginInfo;
	private SharedPreferences sp;
	private Editor editor;

	public static LoginInfo getInstance(Context context) {
		if (loginInfo == null) {
			loginInfo = new LoginInfo(context);
		}
		return loginInfo;
	}

	public LoginInfo(Context context) {
		sp = context.getSharedPreferences(Constant.APP_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();
		editor.commit();
		saveInfo(DesUtil.decode(Constant.KEY, sp.getString("logininfo", "")));
	}

	public boolean isLogin() {
		return getUserid() != 0;
	}

	public void saveInfo(String result) {
		editor.putString("logininfo",
				DesUtil.encode(Constant.KEY, result.toString()));
		editor.commit();
		if (StringUtil.isEmpty(result)) {
			return;
		}
		UserItem userItem = new Gson().fromJson(result, UserItem.class);
		setActivitymsg(userItem.getActivitymsg());
		setAvatar(userItem.getAvatar());
		setBackground(userItem.getBackground());
		setBirthday(userItem.getBirthday());
		setBusinessmsg(userItem.getBusinessmsg());
		setEmail(userItem.getEmail());
		setFans(userItem.isFans());
		setFansCount(userItem.getFansCount());
		setFollowing(userItem.isFollowing());
		setFollowingCount(userItem.getFollowingCount());
		setFriendmsg(userItem.getFriendmsg());
		setHousenumber(userItem.getHousenumber());
		setIntroduction(userItem.getIntroduction());
		setLastActivity(userItem.getLastActivity());
		setName(userItem.getName());
		setPassword(userItem.getPassword());
		setPermission(userItem.getPermission());
		setPhone(userItem.getPhone());
		setServerCount(userItem.getServerCount());
		setTime(userItem.getTime());
		setUserid(userItem.getUserid());
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
		netUtil.setParams("userid", loginInfo.getUserid());
		netUtil.setParams("name", loginInfo.getName());
		netUtil.setParams("password", loginInfo.getRealPassword());
		netUtil.setParams("phone", loginInfo.getPhone());
		netUtil.setParams("housenumber", loginInfo.getHousenumber());
		netUtil.setParams("birthday", loginInfo.getBirthday());
		netUtil.setParams("permission", loginInfo.getPermission());
		netUtil.setParams("background",
				StringUtil.getFileName(loginInfo.getBackground()));
		netUtil.setParams("avatar",
				StringUtil.getFileName(loginInfo.getAvatar()));
		netUtil.setParams("friendmsg", loginInfo.getFriendmsg());
		netUtil.setParams("activitymsg", loginInfo.getActivitymsg());
		netUtil.setParams("businessmsg", loginInfo.getBusinessmsg());
		netUtil.setParams("introduction", loginInfo.getIntroduction());
		netUtil.postRequest(text, stringListener);
	}

	public static void update(Activity activity) {
		update(activity, "", null);
	}

	public void close() {
		loginInfo = null;
	}
}

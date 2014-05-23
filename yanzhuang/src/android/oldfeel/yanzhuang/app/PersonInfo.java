package android.oldfeel.yanzhuang.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.oldfeel.yanzhuang.item.UserItem;
import android.oldfeel.yanzhuang.util.DesUtil;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.LogUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;

import com.google.gson.Gson;

/**
 * 个人信息
 * 
 * @author oldfeel
 * 
 *         Created on: 2014年3月1日
 */
public class PersonInfo {
	private static final String key = "yanzhuan";
	private static PersonInfo appConfig;
	private SharedPreferences sp;
	private Editor editor;
	private UserItem userItem;

	public static PersonInfo getInstance(Context context) {
		if (appConfig == null) {
			appConfig = new PersonInfo(context);
		}
		return appConfig;
	}

	public PersonInfo(Context context) {
		sp = context.getSharedPreferences(Constant.APP_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();
		editor.commit();
		userItem = new Gson().fromJson(
				DesUtil.decode(key, sp.getString("personinfo", "")),
				UserItem.class);
	}

	public boolean isLogin() {
		return userItem != null;
	}

	public void saveInfo(String result) {
		userItem = new Gson().fromJson(result, UserItem.class);
		editor.putString("personinfo", DesUtil.encode(key, result.toString()));
		editor.commit();
	}

	public static void update(Activity activity) {
		PersonInfo personInfo = PersonInfo.getInstance(activity);
		NetUtil netUtil = new NetUtil(activity, JsonApi.UPDATE_USER_INFO);
		netUtil.setParams("userid", personInfo.getUserid());
		netUtil.setParams("name", personInfo.getName());
		netUtil.setParams("phone", personInfo.getPhone());
		netUtil.setParams("housenumber", personInfo.getHouseNumber());
		netUtil.setParams("birthday", personInfo.getBirthday());
		netUtil.setParams("permission", personInfo.getPermission());
		netUtil.setParams("background", personInfo.getBackGround());
		netUtil.setParams("avatar", personInfo.getAvatar());
		netUtil.setParams("friendmsg", personInfo.getFriendmsg());
		netUtil.setParams("activitymsg", personInfo.getActivitymsg());
		netUtil.setParams("businessmsg", personInfo.getBusinesssmg());
		netUtil.postRequest("", new RequestStringListener() {

			@Override
			public void onComplete(String result) {
				if (JSONUtil.isSuccess(result)) {
					LogUtil.showLog("更新用户信息成功");
				} else {
					LogUtil.showLog("更新用户信息失败," + JSONUtil.getMessage(result));
				}
			}
		});
	}

	public String getAvatar() {
		return userItem.getAvatar();
	}

	public String getBackGround() {
		return userItem.getBackground();
	}

	public int getPermission() {
		return userItem.getPermission();
	}

	public String getBirthday() {
		return userItem.getBirthday();
	}

	public String getHouseNumber() {
		return userItem.getHousenumber();
	}

	public String getPhone() {
		return userItem.getPhone();
	}

	public String getName() {
		return userItem.getName();
	}

	public long getUserid() {
		return userItem.getUserid();
	}

	public String getEmail() {
		return userItem.getEmail();
	}

	public String getPassword() {
		return userItem.getPassword();
	}

	public CharSequence getIntroduction() {
		return userItem.getIntroduction();
	}

	public boolean getBusinesssmg() {
		return userItem.getBusinessmsg();
	}

	public boolean getActivitymsg() {
		return userItem.getActivitymsg();
	}

	public boolean getFriendmsg() {
		return userItem.getFriendmsg();
	}

	public void setBusinessmsg(Boolean valueOf) {
		userItem.setBusinessmsg(valueOf);
		saveInfo(new Gson().toJson(userItem));
	}

	public void setActivitymsg(Boolean valueOf) {
		userItem.setActivitymsg(valueOf);
		saveInfo(new Gson().toJson(userItem));
	}

	public void setFriendmsg(Boolean valueOf) {
		userItem.setFriendmsg(valueOf);
		saveInfo(new Gson().toJson(userItem));
	}
}

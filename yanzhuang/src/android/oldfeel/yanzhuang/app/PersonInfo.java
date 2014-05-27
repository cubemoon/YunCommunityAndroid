package android.oldfeel.yanzhuang.app;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.oldfeel.yanzhuang.item.UserItem;
import android.oldfeel.yanzhuang.util.DesUtil;
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

	public static void update(Activity activity, String text,
			RequestStringListener stringListener) {
		PersonInfo personInfo = PersonInfo.getInstance(activity);
		NetUtil netUtil = new NetUtil(activity, JsonApi.UPDATE_USER_INFO);
		netUtil.setParams("userid", personInfo.getUserid());
		netUtil.setParams("name", personInfo.getName());
		netUtil.setParams("password", personInfo.getPassword());
		netUtil.setParams("phone", personInfo.getPhone());
		netUtil.setParams("housenumber", personInfo.getHouseNumber());
		netUtil.setParams("birthday", personInfo.getBirthday());
		netUtil.setParams("permission", personInfo.getPermission());
		netUtil.setParams("background", personInfo.getBackGround());
		netUtil.setParams("avatar", personInfo.getAvatar());
		netUtil.setParams("friendmsg", personInfo.getFriendmsg());
		netUtil.setParams("activitymsg", personInfo.getActivitymsg());
		netUtil.setParams("businessmsg", personInfo.getBusinesssmg());
		netUtil.setParams("introduction", personInfo.getIntroduction());
		netUtil.postRequest(text, stringListener);
	}

	public static void update(Activity activity) {
		update(activity, "", null);
	}

	public void setAvatar(String avatar) {
		userItem.setAvatar(avatar);
		saveInfo(new Gson().toJson(userItem));
	}

	public String getAvatar() {
		return (userItem == null) ? "" : userItem.getAvatar();
	}

	public void setBackGround(String background) {
		userItem.setBackground(background);
		saveInfo(new Gson().toJson(userItem));
	}

	public String getBackGround() {
		return (userItem == null) ? "" : userItem.getBackground();
	}

	public void setPermission(int permission) {
		userItem.setPermission(permission);
		saveInfo(new Gson().toJson(userItem));
	}

	public int getPermission() {
		return (userItem == null) ? 0 : userItem.getPermission();
	}

	public void setBirthday(String birthday) {
		userItem.setBirthday(birthday);
		saveInfo(new Gson().toJson(userItem));
	}

	public String getBirthday() {
		return (userItem == null) ? "" : userItem.getBirthday();
	}

	public void setHouseNumber(String housenumber) {
		userItem.setHousenumber(housenumber);
		saveInfo(new Gson().toJson(userItem));
	}

	public String getHouseNumber() {
		return (userItem == null) ? "" : userItem.getHousenumber();
	}

	public void setPhone(String phone) {
		userItem.setPhone(phone);
		saveInfo(new Gson().toJson(userItem));
	}

	public String getPhone() {
		return (userItem == null) ? "" : userItem.getPhone();
	}

	public void setName(String name) {
		userItem.setName(name);
		saveInfo(new Gson().toJson(userItem));
	}

	public String getName() {
		return (userItem == null) ? "" : userItem.getName();
	}

	public void setUserid(long userid) {
		userItem.setUserid(userid);
		saveInfo(new Gson().toJson(userItem));
	}

	public long getUserid() {
		return (userItem == null) ? 0 : userItem.getUserid();
	}

	public void setEmail(String email) {
		userItem.setEmail(email);
		saveInfo(new Gson().toJson(userItem));
	}

	public String getEmail() {
		return (userItem == null) ? "" : userItem.getEmail();
	}

	public void setPassword(String password) {
		userItem.setPassword(password);
		saveInfo(new Gson().toJson(userItem));
	}

	public String getPassword() {
		return (userItem == null) ? "" : userItem.getPassword();
	}

	public void setIntroduction(String introduction) {
		userItem.setIntroduction(introduction);
		saveInfo(new Gson().toJson(userItem));
	}

	public CharSequence getIntroduction() {
		return (userItem == null) ? "" : userItem.getIntroduction();
	}

	public boolean getBusinesssmg() {
		return (userItem == null) ? true : userItem.getBusinessmsg();
	}

	public boolean getActivitymsg() {
		return (userItem == null) ? true : userItem.getActivitymsg();
	}

	public boolean getFriendmsg() {
		return (userItem == null) ? true : userItem.getFriendmsg();
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

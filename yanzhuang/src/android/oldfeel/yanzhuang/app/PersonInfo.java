package android.oldfeel.yanzhuang.app;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.oldfeel.yanzhuang.R;
import android.oldfeel.yanzhuang.util.DesUtil;
import android.oldfeel.yanzhuang.util.JSONUtil;
import android.oldfeel.yanzhuang.util.LogUtil;
import android.oldfeel.yanzhuang.util.NetUtil;
import android.oldfeel.yanzhuang.util.NetUtil.RequestStringListener;
import android.oldfeel.yanzhuang.util.PreferenceUtil;
import android.oldfeel.yanzhuang.util.StringUtils;

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
	}

	public void saveInfo(String result) {
		setIsAutoLogin(true);
		JSONObject data = JSONUtil.getData(result);
		try {
			long userid = data.getLong("userid");
			setUserid(userid);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			String name = data.getString("name");
			setName(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			String password = data.getString("password");
			setPassword(password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			String email = data.getString("email");
			setEmail(email);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			String phone = data.getString("phone");
			setPhone(phone);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			String housenumber = data.getString("housenumber");
			setHouseNumber(housenumber);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			String birthday = data.getString("birthday");
			setBirthday(birthday);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			int permission = data.getInt("permission");
			setPermission(permission);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			String background = data.getString("background");
			setBackGround(background);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			String avatar = data.getString("avatar");
			setAvatar(avatar);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			boolean friendmsg = data.getBoolean("friendmsg");
			setFriendmsg(friendmsg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			boolean businessmsg = data.getBoolean("businessmsg");
			setBusinessmsg(businessmsg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		try {
			boolean activitymsg = data.getBoolean("activitymsg");
			setActivitymsg(activitymsg);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public boolean getFriendmsg() {
		return sp.getBoolean("friendmsg", true);
	}

	public boolean getBusinesssmg() {
		return sp.getBoolean("businessmsg", true);
	}

	public boolean getActivitymsg() {
		return sp.getBoolean("activitymsg", true);
	}

	public void setFriendmsg(boolean friendmsg) {
		editor.putBoolean("friendmsg", friendmsg);
		editor.commit();
	}

	public void setBusinessmsg(boolean businessmsg) {
		editor.putBoolean("businessmsg", businessmsg);
		editor.commit();
	}

	public void setActivitymsg(boolean activitymsg) {
		editor.putBoolean("activitymsg", activitymsg);
		editor.commit();
	}

	public void setBackGround(String background) {
		editor.putString("background", background);
		editor.commit();
	}

	public String getBackGround() {
		return sp.getString("background", "");
	}

	public void setPermission(int permission) {
		editor.putInt("permission", permission);
		editor.commit();
	}

	public int getPermission() {
		return sp.getInt("permission", 0);
	}

	public void setBirthday(String birthday) {
		editor.putString("birthday", birthday);
		editor.commit();
	}

	public String getBirthday() {
		return sp.getString("birthday", "");
	}

	public void setHouseNumber(String house_number) {
		editor.putString("house_number", house_number);
		editor.commit();
	}

	public String getHouseNumber() {
		return sp.getString("house_number", "");
	}

	public void setPhone(String phone) {
		editor.putString("phone", phone);
		editor.commit();
	}

	public String getPhone() {
		return sp.getString("phone", "");
	}

	public void setEmail(String email) {
		editor.putString("email", email);
		editor.commit();
	}

	public String getEmail() {
		return sp.getString("email", "");
	}

	public void setPassword(String password) {
		password = DesUtil.encode(key, password);
		editor.putString("password", password);
		editor.commit();
	}

	public String getPassword() {
		String password = sp.getString("password", "");
		return DesUtil.decode(key, password);
	}

	public void setIsAutoLogin(boolean isAutoLogin) {
		editor.putBoolean("isautologin", isAutoLogin);
		editor.commit();
	}

	public boolean isAutoLogin() {
		return sp.getBoolean("isautologin", false);
	}

	public void setName(String name) {
		editor.putString("name", name);
		editor.commit();
	}

	public String getName() {
		String name = sp.getString("name", "");
		if (StringUtils.isEmpty(name)) {
			return getEmail();
		}
		return sp.getString("name", "");
	}

	/**
	 * 保存 头像网址
	 * 
	 * @param avatar
	 */
	public void setAvatar(String avatar) {
		editor.putString("avatar", avatar);
		editor.commit();
	}

	public String getAvatar() {
		return sp.getString("avatar", "");
	}

	public void setUserid(long userid) {
		editor.putLong("userid", userid);
		editor.commit();
	}

	public long getUserid() {
		return sp.getLong("userid", -1);
	}

	public static void update(Activity activity) {
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
		netUtil.setParams("friendmsg",
				getBoolean(activity, R.string.friend_msg));
		netUtil.setParams("activitymsg",
				getBoolean(activity, R.string.activity_msg));
		netUtil.setParams("businessmsg",
				getBoolean(activity, R.string.business_msg));
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

	public static boolean getBoolean(Activity activity, int keyid) {
		return PreferenceUtil.getBoolean(activity, keyid);
	}
}

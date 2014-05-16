package android.oldfeel.yanzhuang.app;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.oldfeel.yanzhuang.util.DesUtil;
import android.oldfeel.yanzhuang.util.JSONUtil;
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

	/**
	 * 保存登录信息
	 * 
	 * @param email
	 * @param password
	 */
	public void setLoginInfo(String email, String password) {
		setIsAutoLogin(true);
		setEmail(email);
		setPassword(password);
	}

	public void saveInfo(String result) {
		JSONObject data = JSONUtil.getData(result);
		try {
			long id = data.getLong("id");
			setId(id);
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
			String house_number = data.getString("house_number");
			setHouseNumber(house_number);
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
	}

	private void setBackGround(String background) {
		editor.putString("background", background);
		editor.commit();
	}

	public String getBackGround() {
		return sp.getString("background", "");
	}

	private void setPermission(int permission) {
		editor.putInt("permission", permission);
		editor.commit();
	}

	public int getPermission() {
		return sp.getInt("permission", 0);
	}

	private void setBirthday(String birthday) {
		editor.putString("birthday", birthday);
		editor.commit();
	}

	public String getBirthday() {
		return sp.getString("birthday", "");
	}

	private void setHouseNumber(String house_number) {
		editor.putString("house_number", house_number);
		editor.commit();
	}

	public String getHouseNumber() {
		return sp.getString("house_number", "");
	}

	private void setPhone(String phone) {
		editor.putString("phone", phone);
		editor.commit();
	}

	public String getPhone() {
		return sp.getString("phone", "");
	}

	private void setEmail(String email) {
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

	public void setId(long id) {
		editor.putLong("id", id);
		editor.commit();
	}

	public long getId() {
		return sp.getLong("id", -1);
	}
}

package android.oldfeel.yanzhuang.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.oldfeel.yanzhuang.util.DesUtil;

/**
 * 配置
 * 
 * @author oldfeel
 * 
 *         Created on: 2014年3月1日
 */
public class AppConfig {
	private static final String key = "yanzhuan";
	private static AppConfig appConfig;
	private SharedPreferences sp;
	private Editor editor;

	public static AppConfig getInstance(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig(context);
		}
		return appConfig;
	}

	public AppConfig(Context context) {
		sp = context.getSharedPreferences(Constant.APP_NAME,
				Context.MODE_PRIVATE);
		editor = sp.edit();
		editor.commit();
	}

	/**
	 * 保存登录信息
	 * 
	 * @param username
	 * @param password
	 */
	public void setLoginInfo(String username, String password) {
		editor.putString("username", username);
		editor.commit();
		setIsAutoLogin(true);
		setPassword(password);
	}

	public void setPassword(String password) {
		password = DesUtil.encode(key, password);
		editor.putString("password", password);
		editor.commit();
	}

	public void setIsAutoLogin(boolean isAutoLogin) {
		editor.putBoolean("isautologin", isAutoLogin);
		editor.commit();
	}

	public boolean isAutoLogin() {
		return sp.getBoolean("isautologin", false);
	}

	public String getUserName() {
		return sp.getString("username", "");
	}

	public String getPassword() {
		String password = sp.getString("password", "");
		return DesUtil.decode(key, password);
	}

	/**
	 * 保存 头像网址
	 * 
	 * @param avatarUrl
	 */
	public void setAvatarUrl(String avatarUrl) {
		editor.putString("avatarurl", avatarUrl);
		editor.commit();
	}

	public String getAvatarUrl() {
		return sp.getString("avatarurl", "");
	}
}

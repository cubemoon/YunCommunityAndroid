package android.oldfeel.yanzhuang.util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * json工具类
 * 
 * @author oldfeel
 * 
 *         Created on: 2014年2月17日
 */
public class JSONUtil {
	/**
	 * 判断是否成功
	 * 
	 * @param result
	 * @return
	 */
	public static boolean isSuccess(String result) {
		try {
			JSONObject json = new JSONObject(result);
			return json.getBoolean("result");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取返回的message
	 * 
	 * @param result
	 * @return
	 */
	public static String getMessage(String result) {
		try {
			JSONObject json = new JSONObject(result);
			return json.getString("message");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject getData(String result) {
		try {
			JSONObject json = new JSONObject(result);
			return json.getJSONObject("data");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}

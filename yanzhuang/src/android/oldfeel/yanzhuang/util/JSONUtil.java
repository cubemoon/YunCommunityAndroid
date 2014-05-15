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
		return false;
	}

	/**
	 * 判断版本号查询结果
	 * 
	 * @param result
	 * @return
	 */
	public static boolean isSuccess_Version(String result) {
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

	/**
	 * 获取返回的错误码
	 * 
	 * @param result
	 * @return
	 */
	public static String getErrorCode(String result) {
		try {
			JSONObject json = new JSONObject(result);
			return json.getString("errorCode");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}

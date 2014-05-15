package cn.oldfeel.yanzhuang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期工具类
 * 
 * @author oldfeel
 * 
 *         Created on: 2014-1-14
 */
public class DateUtil {
	/**
	 * 获取当前日期,yyyy-MM-dd格式
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		String date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",
				Locale.getDefault());
		date = sdf.format(new Date());
		return date;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static CharSequence getCurrentTime() {
		String date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss",
				Locale.getDefault());
		date = sdf.format(new Date());
		return date;
	}

	/**
	 * 获取时间差
	 * 
	 * @param startTime
	 * @return
	 */
	public static String dateDiff(String startTime) {
		String format = "yyyy-MM-dd HH:mm:ss";
		String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
				Locale.getDefault()).format(new Date());
		return dateDiff(startTime, endTime, format);
	}

	/**
	 * 获取时间差
	 * 
	 * @param startTime
	 * @param endTime
	 * @param format
	 */
	public static String dateDiff(String startTime, String endTime,
			String format) {
		try {
			String[] startStrings = startTime.split("-");
			String[] endStrings = endTime.split("-");
			int sY = Integer.valueOf(startStrings[0]);
			int sM = Integer.valueOf(startStrings[1]);
			int eY = Integer.valueOf(endStrings[0]);
			int eM = Integer.valueOf(endStrings[1]);

			// 按照传入的格式生成一个simpledateformate对象
			SimpleDateFormat sd = new SimpleDateFormat(format,
					Locale.getDefault());
			long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
			long nh = 1000 * 60 * 60;// 一小时的毫秒数
			long nm = 1000 * 60;// 一分钟的毫秒数
			long ns = 1000;// 一秒钟的毫秒数
			long nMonth = nd * 30;
			long diff;
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff / nd;// 计算差多少天
			long hour = diff / nh;// 计算差多少小时
			long min = diff / nm;// 计算差多少分钟
			long sec = diff / ns;// 计算差多少秒
			long month = diff / nMonth;
			if (month > 12) {
				long year = eY - sY;
				return year + "年前";
			}
			if (day > 45) {
				month = eM - sM;
				return month + "月前";
			}
			if (day > 0) {
				return day + "天前";
			}
			if (hour > 0) {
				return hour + "小时前";
			}
			if (min > 0) {
				return min + "分钟前";
			}
			if (sec > 0) {
				return sec + "秒前";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "刚刚";
	}
}

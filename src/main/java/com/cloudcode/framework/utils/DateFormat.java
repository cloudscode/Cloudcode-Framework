package com.cloudcode.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	public static String millisecondTOHHMMSS(Long millisecond) {
		if (millisecond == null) {
			return "";
		}
		long hour = millisecond / (60 * 60 * 1000);
		long day = hour / 24;
		long minute = (millisecond - hour * 60 * 60 * 1000) / (60 * 1000);
		long second = (millisecond - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
		if (second >= 60) {
			second = second % 60;
			minute += second / 60;
		}
		if (minute >= 60) {
			minute = minute % 60;
			hour += minute / 60;
		}
		if (hour >= 24) {
			hour = hour % 24;
			day += hour / 24;
		}
		String sh = "";
		String sm = "";
		String ss = "";
		if (hour < 10) {
			sh = "0" + String.valueOf(hour);
		} else {
			sh = String.valueOf(hour);
		}
		if (minute < 10) {
			sm = "0" + String.valueOf(minute);
		} else {
			sm = String.valueOf(minute);
		}
		if (second < 10) {
			ss = "0" + String.valueOf(second);
		} else {
			ss = String.valueOf(second);
		}
		return day + "天" + sh + "时" + sm + "分" + ss + "秒";
	}

	public static Date strToDate(String str) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy年MM月dd日 HH时mm分ss秒");
		Date date = null;
		try {
			if (str.length() == 19) {
				SimpleDateFormat dateFormat3 = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				str = str.replace("T", " ");
				date = dateFormat3.parse(str);
			} else if (str.length() > 11) {
				date = dateFormat.parse(str);
			} else {
				SimpleDateFormat dateFormat2 = new SimpleDateFormat(
						"yyyy年MM月dd日");
				date = dateFormat2.parse(str);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date strToDate(String str, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = dateFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String getDate() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
		return dateFormat.format(date);
	}

	public static String getYear() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return dateFormat.format(date);
	}

	public static String getDay() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		return dateFormat.format(date);
	}

	public static String getMonth() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		return dateFormat.format(date);
	}

	public static String dateToStr(Date time) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy年MM月dd日 HH时mm分ss秒");
		return dateFormat.format(time);
	}

	public static String dateToStr(Date time, String format) {
		if (time == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(time);
	}
	public static String getDate(String format) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
}

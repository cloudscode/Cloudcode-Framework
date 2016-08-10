package com.cloudcode.framework.utils;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check {
	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(List list) {
		if (list == null ? false : list.size() == 0 ? false : true) {
			return false;
		}
		return true;
	}

	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(Long str) {
		if (str == null || "".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isEmpty(Date date) {
		return date == null;
	}

	public static boolean isEmpty(Object object) {
		return object == null ? true : object.toString().equals("") ? true
				: false;
	}

	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) str);
		return matcher.matches();
	}
	
	public static boolean isNumber(Object str) {
		if (Check.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) str.toString());
		return matcher.matches();
	}

	public static boolean isNoEmpty(List list) {
		return !isEmpty(list);
	}

	public static boolean isNoEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isNoEmpty(Long lo) {
		return !isEmpty(lo);
	}

	public static boolean isNoEmpty(Date date) {
		return !isEmpty(date);
	}

	public static boolean isNoEmpty(Object object) {
		return !isEmpty(object);
	}
	public static String toString(String str) {
		if(Check.isEmpty(str)){
			return "";
		}
		return str;
	}
	public static Integer toString(Integer str) {
		if(Check.isEmpty(str)){
			return 0;
		}
		return str;
	}
}

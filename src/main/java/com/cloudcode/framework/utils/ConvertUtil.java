package com.cloudcode.framework.utils;

public class ConvertUtil {
	public static int toInt(Object object) {
		if (Check.isNumber(object)) {
			if (object.toString().length() > 9) {
				return 999999999;
			}
			return Integer.valueOf(object.toString());
		}
		return 0;
	}

	public static int toInt(String object) {
		if (Check.isNumber(object)) {
			return Integer.valueOf(object);
		}
		return 0;
	}
}

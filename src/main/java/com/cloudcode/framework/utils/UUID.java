package com.cloudcode.framework.utils;


public abstract class UUID {

	/**
	 * 获得一个32位指定分隔符的UUID串
	 * 
	 * @return UUID
	 */
	public static String generateUUID(char separator) {
		if (separator != '-') {
			return java.util.UUID.randomUUID().toString().replace('-', separator);
		} else {
			return java.util.UUID.randomUUID().toString();
		}
	}

	/**
	 * 获得一个32位不带分隔符的UUID串
	 * 
	 * @return UUID
	 */
	public static String generateUUID() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}

}

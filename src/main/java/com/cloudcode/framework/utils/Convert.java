package com.cloudcode.framework.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

public class Convert {
	/**
	 * 对象转字符串
	 * 
	 * @param object
	 * @return
	 */
	public static String toString(Object object) {
		return object == null ? "" : object.toString();
	}

	/**
	 * 对象List转mapList
	 * 
	 * @param objList
	 * @return
	 */
	public static List<Map<String, Object>> objectListToMapList(
			Collection<Object> objList) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (Object object : objList) {
			mapList.add(objectToMap(object));
		}
		return mapList;
	}

	/**
	 * 对象List转mapList
	 * 
	 * @param objList
	 * @return
	 */
	public static List<Map<String, Object>> objectListToMapListAll(
			Collection<Object> objList) {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (Object object : objList) {
			mapList.add(objectToMapAll(object));
		}
		return mapList;
	}

	/**
	 * 对象转换成map 属性值为空的过滤掉
	 * 
	 * @param obj
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> objectToMap(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean
					.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!StringUtils.equals(name, "class")) {
					Object property = propertyUtilsBean.getNestedProperty(obj,
							name);
					// 把空属性给过滤掉
					if (property != null) {
						params.put(name, property);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * 对象转换成map 属性值为空不过滤
	 * 
	 * @param obj
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> objectToMapAll(Object obj) {
		Map<String, Object> params = new HashMap<String, Object>(0);
		try {
			PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
			PropertyDescriptor[] descriptors = propertyUtilsBean
					.getPropertyDescriptors(obj);
			for (int i = 0; i < descriptors.length; i++) {
				String name = descriptors[i].getName();
				if (!StringUtils.equals(name, "class")) {
					Object property = propertyUtilsBean.getNestedProperty(obj,
							name);
					params.put(name, property);
				} else {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return params;
	}

	/**
	 * 属性转成数据库字段 如： userName TO user_name
	 * 
	 * @param property
	 * @return
	 */
	public static String propertyToField(String property) {
		if (null == property) {
			return "";
		}
		char[] chars = property.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : chars) {
			if (CharUtils.isAsciiAlphaUpper(c)) {
				sb.append("_" + StringUtils.lowerCase(CharUtils.toString(c)));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 字段转换成对象属性 例如：user_name to userName
	 * 
	 * @param field
	 * @return
	 */
	public static String fieldToProperty(String field) {
		if (null == field) {
			return "";
		}
		char[] chars = field.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == '_') {
				int j = i + 1;
				if (j < chars.length) {
					sb.append(StringUtils.upperCase(CharUtils
							.toString(chars[j])));
					i++;
				}
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * list<T>转map<string,T>
	 * 
	 * @param oldcolumnList
	 * @param methodname
	 * @return
	 */
	public static <T> Map<String, T> listToMap(List<T> oldcolumnList,
			String... methodname) {
		Map<String, T> map = new HashMap<String, T>();
		for (T t : oldcolumnList) {
			Object object = ExtendUitl.execute(t,
					methodname.length == 0 ? "getId" : methodname[0]);
			if (Check.isNoEmpty(object)) {
				map.put(Convert.toString(object), t);
			}
		}
		return map;
	}

	/**
	 * map中的某个对象的字段转成字符串 a,v,a
	 * 
	 * @param map
	 * @param methodname
	 * @return
	 */
	public static <T> String mapToStr(Map<String, T> map, String... methodname) {
		StringBuffer stringBuffer = new StringBuffer();
		if (Check.isEmpty(map)) {
			return stringBuffer.toString();
		}
		Set<String> keyset = map.keySet();
		for (String key : keyset) {
			T t = map.get(key);
			Object object = ExtendUitl.execute(t,
					methodname.length == 0 ? "getText" : methodname[0]);
			stringBuffer.append(object + ",");
		}
		if (Check.isEmpty(stringBuffer)) {
			return "";
		}
		return stringBuffer.substring(0, stringBuffer.length() - 1);
	}

	/**
	 * list的map转成map的map
	 * 
	 * @param list
	 * @param propertie
	 *            主键
	 * @return
	 */
	public static Map<String, Map<String, Object>> listMapToMap(
			List<Map<String, Object>> list, String propertie) {
		Map<String, Map<String, Object>> returnMap = new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> map : list) {
			returnMap.put(Convert.toString(map.get(propertie)), map);
		}
		return returnMap;
	}

	/**
	 * 转数字
	 * 
	 * @param object
	 * @return
	 */
	public static int toInt(Object object) {
		if (Check.isNumber(object)) {
			if (object.toString().length() > 9) {
				return 999999999;
			}
			return Integer.valueOf(object.toString());
		}
		return 0;
	}

	/**
	 * 转数字
	 * 
	 * @param object
	 * @return
	 */
	public static int toInt(String object) {
		if (object != null) {
			if (Check.isNumber(object)) {
				return Integer.valueOf(object);
			}
		}
		return 0;
	}

	/**
	 * 转数字
	 * 
	 * @param object
	 * @return
	 */
	public static Long toLong(Object object) {
		if (Check.isNumber(object)) {
			return Long.valueOf(object.toString());
		}
		return 0l;
	}

	/**
	 * 转数字
	 * 
	 * @param object
	 * @return
	 */
	public static Long toLong(String object) {
		if (Check.isNumber(object)) {
			return Long.valueOf(object);
		}
		return 0l;
	}

	/**
	 * a,c,a,d 转 List
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> strToList(String str) {
		if (!Check.isEmpty(str)) {
			String[] arr = str.split(",");
			return arrayToList(arr);
		}
		return new ArrayList<String>();
	}

	/**
	 * 数组转List
	 * 
	 * @param arr
	 * @return
	 */
	public static List<String> arrayToList(String[] arr) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return list;
	}

	/**
	 * 把map中的属性键转成小写
	 * 
	 * @param returnTreeList
	 * @return
	 */
	public static List<Map<String, Object>> dTox(
			List<Map<String, Object>> returnTreeList) {
		if (returnTreeList == null) {
			return null;
		}
		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : returnTreeList) {
			Map<String, Object> map2 = new HashMap<String, Object>();
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				map2.put(key.toLowerCase(), map.get(key));
			}
			treeList.add(map2);
		}
		return treeList;
	}

	/**
	 * 把map中的属性键转成小写
	 * 
	 * @param returnTreeList
	 * @return
	 */
	public static Map<String, Object> dTox(Map<String, Object> map) {
		if (map == null) {
			return null;
		}
		Map<String, Object> map2 = new HashMap<String, Object>();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			map2.put(key.toLowerCase(), map.get(key));
		}
		return map2;
	}

	public static List<String> expressionToListKey(String expression) {
		String string = expression.replaceAll("=", ",").replaceAll("!", ",")
				.replaceAll(">", ",").replaceAll("<", ",")
				.replaceAll("\\+", ",").replaceAll("-", ",")
				.replaceAll("\\*", ",").replaceAll("/", ",")
				.replaceAll("\\(", ",").replaceAll("\\)", ",")
				.replaceAll("\\|", ",").replaceAll("&", ",")
				.replaceAll(" ", "");
		List<String> returnList = new ArrayList<String>();
		for (String string2 : string.split(",")) {
			if (Check.isNoEmpty(string2)) {
				if (validate(string2.replace(".", ""))) {
					returnList.add(string2);
				}
			}
		}
		return returnList;
	}

	public static boolean validate(String input) {
		if (input != null && input.length() > 0) {
			int pos = 0;
			if (Character.isJavaIdentifierStart(input.charAt(pos))) {
				while (++pos < input.length()) {
					if (!Character.isJavaIdentifierPart(input.charAt(pos))) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}

	public static boolean toBoolean(Object parameter) {
		if (parameter == null) {
			return false;
		} else {
			return Boolean.valueOf(parameter.toString());
		}
	}

	public static String toJson(Object variable) {
		if (Check.isEmpty(variable)) {
			return null;
		}
		return new Gson().toJson(variable);
	}
}

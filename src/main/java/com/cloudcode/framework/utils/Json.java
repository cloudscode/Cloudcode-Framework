package com.cloudcode.framework.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json {
	public static Map<String, Object> toMap(String objectStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (objectStr==null) {
			return map;
		}
		try {
			JSONObject jsonObject = new JSONObject(objectStr);
			String[] names = JSONObject.getNames(jsonObject);
			for (String name : names) {
				map.put(name, jsonObject.get(name));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	public static List<Map<String, Object>> toMapList(String objectArrStr) {
		List<Map<String, Object>> mapList =new ArrayList<Map<String,Object>>();
		if (objectArrStr==null) {
			return mapList;
		}
		try {
			JSONArray array = new JSONArray(objectArrStr);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				String[] names = JSONObject.getNames(jsonObject);
				Map<String, Object> map = new HashMap<String, Object>();
				for (String name : names) {
					map.put(name, jsonObject.get(name));
				}
				mapList.add(map);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return mapList;
	}
}

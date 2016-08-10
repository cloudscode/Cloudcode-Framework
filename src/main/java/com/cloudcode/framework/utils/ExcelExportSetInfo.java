package com.cloudcode.framework.utils;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ExcelExportSetInfo {
	private Map<String, List<Map<String, Object>>> objsMap;

	private String[] titles;

	private List<String[]> headNames;

	private List<String[]> fieldNames;

	private OutputStream out;

	public Map<String, List<Map<String, Object>>> getObjsMap() {
		return objsMap;
	}

	/**
	 * @param objMap
	 *            导出数据
	 * 
	 *            泛型 String : 代表sheet名称 List : 代表单个sheet里的所有行数据
	 */
	public void setObjsMap(Map<String, List<Map<String, Object>>> map) {
		this.objsMap = map;
	}

	public List<String[]> getFieldNames() {
		return fieldNames;
	}

	/**
	 * @param clazz
	 *            对应每个sheet里的每行数据的对象的属性名称
	 */
	public void setFieldNames(List<String[]> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public String[] getTitles() {
		return titles;
	}

	/**
	 * @param titles
	 *            对应每个sheet里的标题，即顶部大字
	 */
	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public List<String[]> getHeadNames() {
		return headNames;
	}

	/**
	 * @param headNames
	 *            对应每个页签的表头的每一列的名称
	 */
	public void setHeadNames(List<String[]> headNames) {
		this.headNames = headNames;
	}

	public OutputStream getOut() {
		return out;
	}

	/**
	 * @param out
	 *            Excel数据将输出到该输出流
	 */
	public void setOut(OutputStream out) {
		this.out = out;
	}

}

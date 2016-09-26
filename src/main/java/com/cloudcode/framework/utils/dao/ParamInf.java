package com.cloudcode.framework.utils.dao;

import java.util.Collection;
import java.util.List;

public interface ParamInf extends List<Object> {
	/**
	 * 包含
	 * @param field
	 * @param valueList
	 * @return
	 */
	public ParamInf in(String field, Collection valueList);

	/**
	 * 等于
	 * @param field
	 * @param value
	 * @return
	 */
	public ParamInf is(String field, Object value);

	/**
	 * 排序
	 * @param field
	 * @param order
	 * @return
	 */
	public ParamInf orderDesc(String field);
	/**
	 * 排序
	 * @param field
	 * @param order
	 * @return
	 */
	public ParamInf order(String field);

	/**
	 * 不等于
	 * @param field
	 * @param value
	 * @return
	 */
	public ParamInf nis(String field, Object value);

	public ParamInf le(String string, Object endDate);
	public ParamInf ge(String string, Object value);

	public ParamInf isNull(String field);

	public ParamInf isNotNull(String field);
	
	public ParamInf or(ParamInf paramInf);
	public ParamInf and(ParamInf paramInf);

	public ParamInf like(String field, String value);
	public ParamInf likenoreg(String field, String value);

	public ParamInf nolike(String field, String value);
	public ParamInf nolikenoreg(String field, String value);
	
	public ParamInf in(String field, String[] users);


}

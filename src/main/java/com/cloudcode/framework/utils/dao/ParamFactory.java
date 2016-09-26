package com.cloudcode.framework.utils.dao;

public class ParamFactory {
	public static ParamInf getParam() {
		return new ParamList();
	}
	
	public static ParamInf getParamHb() {
		return new HibernateParamList();
	}
}

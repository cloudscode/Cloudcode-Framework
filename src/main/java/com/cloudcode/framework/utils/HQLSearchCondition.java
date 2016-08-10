package com.cloudcode.framework.utils;

public class HQLSearchCondition {
	private HQLParamList hqlParamList = new HQLParamList();
	private HQLOrderList hqlOrderList = new HQLOrderList();

	public HQLParamList getHqlParamList() {
		return hqlParamList;
	}

	public void setHqlParamList(HQLParamList hqlParamList) {
		this.hqlParamList = hqlParamList;
	}

	public HQLOrderList getHqlOrderList() {
		return hqlOrderList;
	}

	public void setHqlOrderList(HQLOrderList hqlOrderList) {
		this.hqlOrderList = hqlOrderList;
	}

}

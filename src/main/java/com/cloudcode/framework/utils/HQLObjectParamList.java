package com.cloudcode.framework.utils;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class HQLObjectParamList extends ArrayList<Object> {
	public HQLObjectParamList addCondition(Object criterion) {
		this.add(criterion);
		return this;
	}
}

package com.cloudcode.framework.utils.dao;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;

@SuppressWarnings("serial")
public class ParamList extends ArrayList<Object> implements ParamInf {

	
	public ParamInf orderDesc(String field) {
			this.add(new Sort.Order(Sort.Direction.DESC, field));
		return this;
	}

	
	public ParamInf order(String field) {
		this.add(new Sort.Order(Sort.Direction.ASC, field));
		return this;
	}

	
	public ParamInf in(String field, Collection valueList) {
		this.add(new Criteria(field).in(valueList));
		return this;
	}

	
	public ParamInf is(String field, Object value) {
		this.add(new Criteria(field).is(value));
		return this;
	}

	
	public ParamInf nis(String field, Object value) {
		this.add(new Criteria(field).ne(value));
		return this;
	}

	
	public ParamInf like(String field, String value) {
		this.add(new Criteria(field).regex(value));
		return this;
	}
	
	
	public ParamInf likenoreg(String field, String value) {
		this.add(new Criteria(field).regex(value));
		return this;
	}


	
	public ParamInf le(String field, Object value) {
		this.add(new Criteria(field).ne(value));
		return this;
	}

	
	public ParamInf isNull(String field) {
		
		return this;
	}

	
	public ParamInf isNotNull(String field) {
		
		return this;
	}

	
	public ParamInf or(ParamInf paramInf) {
		
		return this;
	}

	
	public ParamInf in(String field, String[] users) {
		
		return this;
	}

	
	public ParamInf ge(String string, Object value) {
		
		return this;
	}

	
	public ParamInf nolike(String field, String value) {
		
		return null;
	}

	
	public ParamInf nolikenoreg(String field, String value) {
		
		return null;
	}

	
	public ParamInf and(ParamInf paramInf) {
		
		return null;
	}

}

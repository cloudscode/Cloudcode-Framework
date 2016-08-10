package com.cloudcode.framework.dao;

import org.springframework.stereotype.Repository;

import com.cloudcode.framework.dao.impl.BaseDaoImpl;
import com.cloudcode.framework.model.BaseModelObject;

@Repository
public class BaseModelObjectDao<T extends BaseModelObject> extends
		BaseDaoImpl<T> {
	public Object create(T obj) {
		return this.getSessionFactory().getCurrentSession().save(obj);
	}
}

package com.cloudcode.framework.rest.impl;

import com.cloudcode.framework.model.ModelObject;

public interface BaseModelObjectService<T extends ModelObject> {
	public <T> void save(T entity);
	public <T> void saveOrUpdate(T entity);
	public <T> void delete(T entity);
	
}

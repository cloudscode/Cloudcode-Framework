/**
 * Project Name:Cloudcode-Framework
 * File Name:BaseMongoDAOImpl.java
 * Package Name:com.cloudcode.framework.dao.mongo.impl
 * Date:2016-8-17下午5:54:46
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
*/

package com.cloudcode.framework.dao.mongo.impl;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.cloudcode.framework.dao.mongo.BaseMongoDAO;
import com.cloudcode.framework.utils.ReflectionUtils;

public abstract class BaseMongoDAOImpl<T> implements BaseMongoDAO<T>{

	private static final int DEFAULT_SKIP = 0;
	private static final int DEFAULT_LIMIT = 200;
	
	/**
	 * spring mongodb　集成操作类　
	 */
	protected MongoTemplate mongoTemplate;

	public List<T> find(Query query) {
		return mongoTemplate.find(query, this.getEntityClass());
	}

	public T findOne(Query query) {
		return mongoTemplate.findOne(query, this.getEntityClass());
	}

	public void update(Query query, Update update) {
		mongoTemplate.findAndModify(query, update, this.getEntityClass());
	}

	public T insert(T entity) {
		mongoTemplate.insert(entity);
		return entity;
	}
	public T save(T entity) {
		mongoTemplate.save(entity);
		return entity;
	}
	public T findById(String id) {
		return mongoTemplate.findById(id, this.getEntityClass());
	}

	public T findById(String id, String collectionName) {
		return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
	}
	
	public Page<T> findPage(Page<T> page,Query query){
		long count = this.count(query);
		/*page.setTotal(count);
		int pageNumber = page.getPageNumber();
		int pageSize = page.getPageSize();
		query.skip((pageNumber - 1) * pageSize).limit(pageSize);
		List<T> rows = this.find(query);
		page.setRows(rows);*/
		return page;
	}
	
	public long count(Query query){
		return mongoTemplate.count(query, this.getEntityClass());
	}
	

	/**
	 * 获取需要操作的实体类class
	 * 
	 * @return
	 */
	private Class<T> getEntityClass(){
		return ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 注入mongodbTemplate
	 * 
	 * @param mongoTemplate
	 */
	protected abstract void setMongoTemplate(MongoTemplate mongoTemplate);

}

package com.cloudcode.framework.hibernate;

import org.hibernate.criterion.DetachedCriteria;

import com.cloudcode.framework.utils.Condition;


public interface HibernateCondition extends Condition {

	/**
	 * 根据condition装配查询条件
	 * @param criteria
	 */
	public void populateDetachedCriteria(DetachedCriteria criteria);
}

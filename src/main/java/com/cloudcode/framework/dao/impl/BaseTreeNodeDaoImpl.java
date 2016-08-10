package com.cloudcode.framework.dao.impl;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Cache;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import com.cloudcode.framework.dao.TreeNodeModelObjectDao;
import com.cloudcode.framework.model.BaseTreeNodeModelObject;

public class BaseTreeNodeDaoImpl<T extends BaseTreeNodeModelObject<T>> extends BaseDaoImpl<T> implements TreeNodeModelObjectDao<T>{
	
	public BaseTreeNodeDaoImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}
	public BaseTreeNodeDaoImpl() {
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<T> queryList(Class<T> class1, List<Object> list) {
		Criteria criteria = this.getSession().createCriteria(class1);
		for (Object object : list) {
			if (object instanceof Criterion) {
				criteria.add((Criterion) object);
			} else if (object instanceof Order) {
				criteria.addOrder((Order) object);
			}
		}
		//order(class1, criteria);
		if (class1.isAnnotationPresent((Class<? extends Annotation>) Cache.class)) {
			criteria.setCacheable(true);
		}
		return criteria.list();
	}
	
	public List<T> queryTreeList(Class<T> class1, List<Object> list) {
		List<T> objectList = this.queryList(class1, list);
		for (T object : objectList) {
			addChildren(class1, list, object);
		}
		return objectList;
	}
	
	private void addChildren(Class<T> class1, List<Object> list, T object) {
		List<Object> objeList =  new ArrayList<Object>();
		for (Object object1 : list) {
			if (object1 instanceof SimpleExpression) {
				if ("node".equals(((SimpleExpression)object1).getPropertyName())) {
					continue;
				}
			}
			objeList.add(object1);
		}
		if (object.getLeaf() == 0 && object.getExpanded() == 1) {
			objeList.add(Restrictions.eq("node", object.getId()));
			List<T> children = this.queryTreeList(class1, objeList);
			for (T t : children) {
				addChildren(class1, list, t);
			}
			object.setChildren(children);
		}
	}
//	@Override
	public T loadObject(Class<T> entityClass, String id) {
		// TODO Auto-generated method stub
		return null;
	}
	//@Override
	public void deleteObjects(String[] ids) {
		// TODO Auto-generated method stub
		
	}
	//@Override
	public void deleteObjects(T[] entities) {
		// TODO Auto-generated method stub
		
	}
	//@Override
	public void deleteObjects(Collection<T> entities) {
		// TODO Auto-generated method stub
		
	}
	//@Override
	public List<T> getAll() {
		// TODO Auto-generated method stub
		return null;
	}
	//@Override
	public List<T> getAll(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}
}

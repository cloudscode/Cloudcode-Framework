package com.cloudcode.framework.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.cglib.beans.BeanMap;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.cloudcode.framework.annotation.AnnotationUtils;
import com.cloudcode.framework.dao.ModelObjectDao;
import com.cloudcode.framework.exception.PrimaryKeyUndefinedException;
import com.cloudcode.framework.model.ModelObject;
import com.cloudcode.framework.utils.Check;
import com.cloudcode.framework.utils.ConvertUtil;
import com.cloudcode.framework.utils.ModelObjectUtils;
import com.cloudcode.framework.utils.PageRange;
import com.cloudcode.framework.utils.PaginationSupport;
import com.cloudcode.framework.utils.UUID;

@Repository
public class BaseDaoImpl<T extends ModelObject>  extends HibernateDaoSupport implements ModelObjectDao<T>{

	private final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return this.jdbcTemplate;
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("【数据库链接获取失败：】" + e.getMessage());
		}
		return conn;
	}
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
	    super.setSessionFactory(sessionFactory);

	}
	@Autowired
	//@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

/*	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}
*/
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void clear() {
		this.getSession().clear();
	}

	public void flush() {
		this.getSession().flush();
	}

	protected Boolean snapshot;

	protected Class<T> entityClass;
	private String entityName;
	private String entityPK;

	public Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
			logger.debug("T class = " + entityClass.getName());
		}
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public BaseDaoImpl() {
	}

	public BaseDaoImpl(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public Class<T> getEntityType() {
		if (this.entityClass == null) {
			ParameterizedType pmType = (ParameterizedType) getClass()
					.getGenericSuperclass();
			this.entityClass = ((Class) pmType.getActualTypeArguments()[0]);
		}
		return this.entityClass;
	}

	public String getEntityName() {
		if (this.entityName == null) {
			this.entityName = ModelObjectUtils.getEntityName(getEntityType());
		}
		return this.entityName;
	}

	public String getEntityPK() {
		if (this.entityPK == null) {
			this.entityPK = AnnotationUtils.getPrimaryKeyName(getEntityClass());
			if (this.entityPK == null) {
				throw new PrimaryKeyUndefinedException(
						"Primary key is undefined: " + getEntityName());
			}
		}
		return this.entityPK;
	}

	protected String getEntityPrimaryKeyValue(T entity) {
		String pk = getEntityPK();
		BeanMap map = BeanMap.create(entity);
		return (String) map.get(pk);
	}
	public void deleteObjects(String[] ids) {
		   for (String id : ids)
		      deleteObject(id);
	}
	public void deleteObject(String id) {
		T entity = (T) this.getSession().get(this.getEntityClass(), id);
		this.getSession().delete(entity);
		this.getSession().flush();
		this.getSession().clear();
		// this.getSession().close();
	}

	public void deleteObject(T entity) {
		this.getSession().delete(entity);
		this.getSession().flush();
		this.getSession().clear();
	}

	public void deleteObject(Class<T> entityClass, T entity) {
		this.getSession().delete(entityClass.getName(), entity);
	}

	public int deleteObject(Class<T> class1, String property, List<String> list) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"delete " + class1.getName() + " o where o." + property
								+ " IN (:" + property + ")")
				.setParameterList(property, list).executeUpdate();
	}

	public void deleteAll() {
		List<T> entities = loadAll();
		for (T entity : entities) {
			deleteObject(entity);
		}
	}

	protected String checkAndInitEntityPK(T entity) {
		String pk = getEntityPK();
		BeanMap map = BeanMap.create(entity);
		String entityKey = (String) map.get(pk);
		if (StringUtils.isBlank(entityKey)) {
			entityKey = UUID.generateUUID();
			map.put(pk, entityKey);
		}
		return entityKey;
	}

	public String createObject(T entity) {
		checkAndInitEntityPK(entity);
		String entityId = (String) this.getSessionFactory().getCurrentSession()
				.save(entity);
		this.getSession().flush();
		this.getSession().clear();
		return entityId;
	}

	public String createObject(Class<T> entityClass, T entity) {
		String entityKey = checkAndInitEntityPK(entity);
		String entityId = (String) this.getSession().save(
				entityClass.getName(), entityKey);
		return entityId;
	}

	public void saveOrUpdate(T entity) {
		String entityKey = checkAndInitEntityPK(entity);
		this.getSession().saveOrUpdate(entity);
	}

	public void saveOrUpdate(Class<T> entityClass, T entity) {
		String entityKey = checkAndInitEntityPK(entity);
		this.getSession().saveOrUpdate(entityClass.getName(), entity);
	}

	public void updateObject(T entity) {
		this.getSession().update(entity);
		this.getSession().flush();
		this.getSession().clear();
		// this.getSession().close();
	}

	public int updateObject(String hql, T entity) {
		return sessionFactory.getCurrentSession().createQuery(hql)
				.setProperties(entity).executeUpdate();
	}

	public int updateProperty(Class<T> class1, String id, String key,
			Object value) {
		Query query = this.getSession().createQuery(
				"update " + class1.getName() + " set " + key
						+ "=? where id  = ?");
		query.setParameter(0, value);
		query.setParameter(1, id);
		return query.executeUpdate();
	}

	public void evict(T entity) {
		this.getSession().evict(entity);
	}

	public void saveOrUpdateEntity(T entity) {
		this.getSession().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public List<T> loadAll() {
		Criteria criteria = this.getSession().createCriteria(
				this.getEntityClass());
		// order(entityClass, criteria);
		if (entityClass
				.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		return criteria.list();
		// return (List<T>)this.getSession().get(this.getEntityClass(),0);
	}

	public List<T> loadAll(Criteria criteria) {

		// if (entityClass
		// .isAnnotationPresent(Cache.class)) {
		// criteria.setCacheable(true);
		// }
		return criteria.list();
		// return (List<T>)this.getSession().get(this.getEntityClass(),0);
	}

	@org.springframework.transaction.annotation.Transactional
	public T loadObject(String id) {
		return (T) this.getSession().get(this.getEntityClass(), id);
	}

	public T loadObject(Class<T> entityClass, String id) {
		return (T) this.getSession().get(this.getEntityClass(), id);
	}

	public PaginationSupport<T> queryPaginationSupport(String whereHQL,
			T entity, PageRange pageRange) {
		PaginationSupport<T> pageData = new PaginationSupport<T>();
		Query query = this.getSession().createQuery(
				"select o from " + entity.getClass().getName() + " o ");
		query.setProperties(entity);
		query.setFirstResult(pageRange.getStart());
		query.setMaxResults(pageRange.getRows());
		if (entity.getClass().isAnnotationPresent(Cache.class)) {
			query.setCacheable(true);
		}
		List<T> items = query.list();
		pageData.setRows(items);
		query = this.getSession().createQuery(
				"select count(o) from " + entity.getClass().getName() + " o ");
		// 设置HQL语句参数
		query.setProperties(entity);
		if (entity.getClass().isAnnotationPresent(Cache.class)) {
			query.setCacheable(true);
		}
		pageData.setPage(pageRange.getPage());
		pageData.setRecords(ConvertUtil.toInt(query.uniqueResult()));
		pageData.setRows(query.list());
		pageData.setTotal((int) Math.rint(ConvertUtil.toInt(ConvertUtil
				.toInt(query.uniqueResult())) / pageRange.getRows()));
		return pageData;
	}

	public PaginationSupport<T> queryPaginationSupport(Class<T> class1,
			List<Object> list, PageRange pageRange) {
		return this.queryPaginationSupport(class1, list, pageRange,
				new ArrayList<String>());
	}

	public PaginationSupport<T> queryPaginationSupport(Class<T> class1,
			PageRange pageRange) {
		return this.queryPaginationSupport(class1, new ArrayList<Object>(),
				pageRange);
	}

	public Map<String, Object> queryPaginationSupport(String sql,
			String countSql, PageRange pageRange) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		SQLQuery countSqlQuery = this.getSession().createSQLQuery(countSql);

		sqlQuery.setFirstResult(pageRange.getStart());
		sqlQuery.setMaxResults(pageRange.getRows());
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		resultMap.put("items", sqlQuery.list());
		resultMap.put("total", countSqlQuery.uniqueResult());
		return resultMap;
	}

	public Map<String, Object> queryPaginationSupport(String sql,
			String countSql, Map<String, Object> paramMap, PageRange pageRange) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SQLQuery sqlQuery = this.getSession().createSQLQuery(sql);
		SQLQuery countSqlQuery = this.getSession().createSQLQuery(countSql);

		sqlQuery.setProperties(paramMap);
		countSqlQuery.setProperties(paramMap);
		sqlQuery.setFirstResult(pageRange.getStart());
		sqlQuery.setMaxResults(pageRange.getRows());
		sqlQuery.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		resultMap.put("items", sqlQuery.list());
		resultMap.put("total", countSqlQuery.uniqueResult());
		return resultMap;
	}

	public PaginationSupport<T> queryPaginationSupport(Class<T> class1,
			List<Object> hqlParamList, PageRange pageRange,
			List<String> selectProperList) {

		PaginationSupport<T> pageData = new PaginationSupport<T>();
		Criteria criteria = this.getSession().createCriteria(class1);
		Criteria criteriaCount = this.getSession().createCriteria(class1);
		for (Object object : hqlParamList) {
			if (object instanceof Criterion) {
				criteria.add((Criterion) object);
				criteriaCount.add((Criterion) object);
			} else if (object instanceof Order) {
				criteria.addOrder((Order) object);
			}
		}
		criteria.setFirstResult(pageRange.getStart());
		criteria.setMaxResults(pageRange.getRows());
		if (Check.isNoEmpty(selectProperList)) {
			ProjectionList projectionList = Projections.projectionList();
			for (String prperties : selectProperList) {
				projectionList.add(Projections.property(prperties)
						.as(prperties));
			}
			criteria.setProjection(projectionList);
			criteria.setResultTransformer(Transformers.aliasToBean(class1));
		}
		// order(class1, criteria);
		if (class1
				.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
			criteriaCount.setCacheable(true);
		}
		pageData.setPage(pageRange.getPage());
		pageData.setRecords(ConvertUtil.toInt(criteriaCount.setProjection(
				Projections.rowCount()).uniqueResult()));
		pageData.setRows(criteria.list());
		pageData.setTotal((int) Math.rint(ConvertUtil.toInt(criteriaCount
				.setProjection(Projections.rowCount()).uniqueResult())
				/ pageRange.getRows()));
		return pageData;
	}

	public PaginationSupport<T> queryPaginationSupport(Class<T> class1,
			List<Object> hqlParamList, PageRange pageRange,
			String[] selectPropers) {

		List<String> selectProperList = new ArrayList<String>();
		for (String string : selectPropers) {
			selectProperList.add(string);
		}
		return this.queryPaginationSupport(class1, hqlParamList, pageRange,
				selectProperList);
	}

	public List<T> queryList(Class<T> class1, Criterion criterion) {
		Criteria criteria = this.getSession().createCriteria(class1)
				.add(criterion);
		// order(class1, criteria);
		if (class1
				.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		return criteria.list();
	}

	public List<T> queryList(Class<T> class1, String propertiesName,
			List<String> param) {
		return this.queryList(class1, Restrictions.in(propertiesName, param));
	}

	public List<T> queryList(Class<T> class1, String propertiesName,
			String param) {
		return this.queryList(class1, Restrictions.eq(propertiesName, param));
	}

	public List<T> queryList(String hql, T entity) {
		Query query = this.getSession().createQuery(hql);
		if (entity.getClass().isAnnotationPresent(Cache.class)) {
			query.setCacheable(true);
		}
		return query.setProperties(entity).list();
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
		// order(class1, criteria);
		if (class1
				.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		return criteria.list();
	}

	public T findEntity(Class<T> class1, List<Object> list) {
		Criteria criteria = this.getSession().createCriteria(class1);
		for (Object object : list) {
			if (object instanceof Criterion) {
				criteria.add((Criterion) object);
			} else if (object instanceof Order) {
				criteria.addOrder((Order) object);
			}
		}
		if (class1
				.isAnnotationPresent(Cache.class)) {
			criteria.setCacheable(true);
		}
		return (T) criteria.uniqueResult();
	}

	public T findEntityByPK(Class entityClass, Serializable id) {
		return (T) this.sessionFactory.getCurrentSession().get(entityClass, id);
	}

	public Object findPropertys(T entity, String[] returnPropertys,
			String[] wherePropertys) {
		StringBuffer hql = new StringBuffer("select ");
		for (int i = 0; i < returnPropertys.length; i++) {
			String string = returnPropertys[i];
			hql.append((i == 0 ? "" : " , ") + "o." + string);
		}
		hql.append(" from " + entity.getClass().getName() + " o where ");
		for (int i = 0; i < wherePropertys.length; i++) {
			String string = wherePropertys[i];
			hql.append((i == 0 ? "" : " and ") + " o." + string + "=:" + string);
		}
		Query query = this.getSession().createQuery(hql.toString());
		query.setProperties(entity);
		return query.uniqueResult();
	}

	public Criteria getCriteria(Class<T> obj) {
		Criteria criterion = this.getSession().createCriteria(obj.getClass());
		return criterion;
	}

	public List<Map<String, Object>> queryForMapListBySQL(String sql,
			Object[] args) {
		Query query = getSession().createSQLQuery(sql);

		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List dataList = query.list();
		return dataList;
	}
	public List<T> findByProperty(String propName, Object propValue) {
	    DetachedCriteria criteria = DetachedCriteria.forClass(getEntityType());
	    criteria.add(Restrictions.eq(propName, propValue));
	    List list = null;
	   /* if (this.isSecondCache) {
	      getHibernateTemplate().setCacheQueries(true);
	      list = getHibernateTemplate().findByCriteria(criteria);
	      getHibernateTemplate().setCacheQueries(false);
	    } else {*/
	      list = getHibernateTemplate().findByCriteria(criteria);
//	    }
	    return list;
	  }

	  public T findObject(String propName, Object propValue) {
	    DetachedCriteria criteria = DetachedCriteria.forClass(getEntityType());
	    criteria.add(Restrictions.eq(propName, propValue));
	    List list = null;
	   /* if (this.isSecondCache) {
	      getHibernateTemplate().setCacheQueries(true);
	      list = getHibernateTemplate().findByCriteria(criteria, -1, 1);
	      getHibernateTemplate().setCacheQueries(false);
	    } else {*/
	      list = getHibernateTemplate().findByCriteria(criteria, -1, 1);
//	    }
	    Object entity = list.isEmpty() ? null : list.get(0);
	    return (T)entity;
	  }
}

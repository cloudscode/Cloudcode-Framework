package com.cloudcode.framework.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;

import com.cloudcode.framework.model.ModelObject;
import com.cloudcode.framework.utils.PageRange;
import com.cloudcode.framework.utils.PaginationSupport;

public interface ModelObjectDao<T extends ModelObject> {
	public JdbcTemplate getJdbcTemplate();

	public Connection getConnection();

	public SessionFactory getSessionFactory();

	public Session getSession();

	public Class<T> getEntityType();

	public String getEntityName();

	public String getEntityPK();

	// ~ CRUD of model object

	public String createObject(T entity);

	public String createObject(Class<T> entityClass, T entity);

	public void updateObject(T entity);
	public int updateObject(String hql, T entity);
	public int updateProperty(Class<T> class1, String id, String key,
			Object value);
	/**
	 * 保存一个实体对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	public void saveOrUpdateEntity(T entity);

	public void deleteObject(String id);
	public void deleteObjects(String[] ids);
	public void deleteObject(T entity);

	public void deleteObject(Class<T> entityClass, T entity);

	public int deleteObject(Class<T> class1, String property, List<String> list);
	
	public void deleteAll();

	public void evict(T entity);

	public List<T> loadAll();

	public T loadObject(Class<T> entityClass, String id);

	public T loadObject(String id);
	// ~ query
	public PaginationSupport<T> queryPaginationSupport(String whereHQL, T entity,
			PageRange pageRange);

	/**
	 * 
	 * @param class1
	 *            类型
	 * @param paramList
	 *            条件
	 * @param pageRange
	 *            分页参数
	 * @return map的键 是 items（结果集） 和 total（总条数）
	 */
	public PaginationSupport<T> queryPaginationSupport(Class<T> class1, List<Object> list,
			PageRange pageRange);

	public PaginationSupport<T> queryPaginationSupport(Class<T> class1,
			List<Object> hqlParamList, PageRange pageRange,
			List<String> selectProperList);

	public PaginationSupport<T> queryPaginationSupport(Class<T> class1,
			List<Object> hqlParamList, PageRange pageRange,
			String[] selectPropers);

	/**
	 * 
	 * @param class1
	 *            类型
	 * @param pageRange
	 *            分页参数
	 * @return map的键 是 items（结果集） 和 total（总条数）
	 */
	public PaginationSupport<T> queryPaginationSupport(Class<T> class1, PageRange pageRange);

	public Map<String, Object> queryPaginationSupport(String sql, String countSql,
			PageRange pageRange);

	public Map<String, Object> queryPaginationSupport(String sql, String countSql,
			Map<String, Object> paramMap, PageRange pageRange);
	
	public List<T> queryList(String hql, T entity);

	public List<T> queryList(Class<T> class1, String propertiesName,
			List<String> param);

	public List<T> queryList(Class<T> class1, String propertiesName,
			String param);
	
	public List<T> queryList(Class<T> class1, Criterion criterion);
	public List<T> queryList(Class<T> class1, List<Object> list);
	public T findEntity(Class<T> class1, List<Object> list);
	
	public T findEntityByPK(Class entityClass, Serializable id);
	
	public Object findPropertys(T entity, String[] returnPropertys,
			String[] wherePropertys);
	public Criteria getCriteria(Class<T>  class1);
	public List<Map<String, Object>> queryForMapListBySQL(String sql, Object[] args);
	public List<T> findByProperty(String propName, Object propValue);
	public T findObject(String propName, Object propValue);
}

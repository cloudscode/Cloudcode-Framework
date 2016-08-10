package com.cloudcode.framework.dao;

import java.util.Collection;
import java.util.List;

import com.cloudcode.framework.model.BaseTreeNodeModelObject;

public interface TreeNodeModelObjectDao<T extends BaseTreeNodeModelObject<T>>{
	public Class<T> getEntityType();
	public String getEntityName();
	public String getEntityPK();
	
	// ~ CRUD of model object

    public T loadObject(String id);
    
    public T loadObject(Class<T> entityClass,String id);
    
    public String createObject(T entity);
    public String createObject(Class<T> entityClass,T entity);
    
    public void updateObject(T entity);
    
    public int updateProperty(Class<T> class1, String id, String key,
			Object value);
    
    public void deleteObject(String id);
    
    public void deleteObject(T entity);
    public void deleteObject(Class<T> entityClass,T entity);

    public void deleteObjects(String[] ids);

    public void deleteObjects(T[] entities);

    public void deleteObjects(Collection<T> entities);

    public void deleteAll();	
	
	public List<T> getAll();
	public List<T> getAll(Class<T> entityClass);
	public void saveOrUpdate(T entity);
	public void saveOrUpdate(Class<T> entityClass,T entity);
    public void evict(T entity);
    
    public List<T> queryTreeList(Class<T> class1, List<Object> list);
}

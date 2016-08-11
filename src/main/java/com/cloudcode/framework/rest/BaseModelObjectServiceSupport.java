package com.cloudcode.framework.rest;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cloudcode.framework.dao.ModelObjectDao;
import com.cloudcode.framework.dao.impl.BaseDaoImpl;
import com.cloudcode.framework.model.ModelObject;


//@Repository
public abstract class BaseModelObjectServiceSupport<T extends ModelObject>  {

	@Bean(name="commonDao")
	public ModelObjectDao<T> generateCommonDao(){
		return new BaseDaoImpl<T>((Class<T>) ModelObject.class);
	}
	@Resource(name="commonDao")	
	ModelObjectDao<T> commonDao;
	/*private BaseDaoImpl<T> commonDao;*/

	public void save(T entity) {
		commonDao.createObject(entity);
	}

	public void saveOrUpdate(T entity) {
		//commonDao.saveOrUpdate(entity);
	}

	public  void delete(T entity) {
		commonDao.deleteObject(entity);
	}
	public List<T> LoadAll() {
		return null;// commonDao.getAll();
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Object retrieve(@PathVariable("id") String id) {
		ModelObject object =null;// this.commonDao.loadObject(id);
		return object;
	}  
}

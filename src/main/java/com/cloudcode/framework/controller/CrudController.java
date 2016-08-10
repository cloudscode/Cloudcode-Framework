package com.cloudcode.framework.controller;


import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriTemplate;

import com.cloudcode.framework.ProjectConfig;
import com.cloudcode.framework.dao.ModelObjectDao;
import com.cloudcode.framework.dao.impl.BaseDaoImpl;
import com.cloudcode.framework.model.ModelObject;
import com.google.gson.Gson;

public abstract class CrudController<T extends ModelObject> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static Gson gson = new Gson();
    public CrudController() {
        super();
    }
	//private static Log logger = LogFactory.getLog(CrudController.class);
	protected Class<T> entityClass;
	
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
	public String returnGson(Map<String, Object> map) {
		return this.gson.toJson(map);
	}
	
    //Bring in a generic DAO that can handle crud operations.
	@Resource(name=ProjectConfig.PREFIX+"modelDao")	
	ModelObjectDao<T> modelDao;
	
	@Bean(name="cloudcode.commonDao")
	public ModelObjectDao<T> generateCommonDao(){
		return new BaseDaoImpl<T>(getEntityClass());
	}  

    
    
  /*  @SuppressWarnings("unchecked")
    protected Class<T> getEntityClass()
    {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    
    @SuppressWarnings("unchecked")
    protected T getEntityInstance()
    {
            try {
                return (T) Class.forName(getEntityClass().getName()).newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
    }
    
     //assume the primary key property is going to be the Entity Class plus Seq
     protected String getPkParam()
     {
         return ClassUtils.getShortNameAsProperty(getEntityClass()) + "Seq";
     }*/
    
  /*  @RequestMapping(method = RequestMethod.GET)
    public ModelMap list(ModelMap model) {
        model.addAttribute(modelDao.getAll());
        return model;
    }*/
   /* @RequestMapping(value="/contact/view.action")
	public @ResponseBody Map<String,? extends Object> view(){
		//model.addAttribute("movie", name);    	
		return this.getMap((List<T>) modelDao.getAll(this.getEntityClass()));

	}
*/
	private Map<String, Object> getMap(List<T> user) {

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("total", user.size());
		modelMap.put("data", user);
		modelMap.put("success", true);

		return modelMap;
	}
	/**
	 * Requesting and entity
	 * 
	 * @param id
	 * @return
	 */
/*	@RequestMapping(value = "/{pk}", method = RequestMethod.GET)
	@ResponseBody
	public T get(@PathVariable("pk") Long id, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}*/
	/*@RequestMapping(value = "/retrieve/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object retrieve(@PathVariable("id") String id) {
		return modelDao.loadObject(this.getEntityClass(),id);
	}*/
	
	 @RequestMapping(method = RequestMethod.POST, value = "/create/{id}")
	    @ResponseStatus(HttpStatus.CREATED)
	    public void create(@RequestBody @Valid T x, HttpServletRequest request, HttpServletResponse response) {
		 	String id = modelDao.createObject(x);
	        String requestUrl = request.getRequestURL().toString();
	        URI uri = new UriTemplate("{requestUrl}/{id}").expand(requestUrl,id);
	        response.setHeader("Location", uri.toASCIIString());
	    }

	  /* @RequestMapping(method = RequestMethod.PUT, value = "/update/{id}")
	    @ResponseStatus(HttpStatus.OK)
	    public void update(@PathVariable String id, @RequestBody @Valid T x) {
	        T a = modelDao.loadObject(this.getEntityClass(),id);
	        if (a == null) {
	            logger.warn("User dengan id [{}] tidak ditemukan");
	            throw new IllegalStateException();
	        }
	        modelDao.saveOrUpdate(this.getEntityClass(),x);
	    }

	     @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
	    @ResponseStatus(HttpStatus.OK)
	    public void delete(@PathVariable String id) {
	    	 T a = modelDao.loadObject(this.getEntityClass(),id);
	        if (a == null) {
	            logger.warn("User dengan id [{}] tidak ditemukan");
	            throw new IllegalStateException();
	        }
	        modelDao.deleteObject(this.getEntityClass(),a);
	    }*/
  /*  @RequestMapping(method = RequestMethod.GET)
    public String create(ModelMap model, WebRequest request) {
        return ClassUtils.getShortName(getEntityClass()).toLowerCase() + "/form";
    }*/
		public ModelAndView list() {
			// TODO Auto-generated method stub
			return null;
		}

   /* @RequestMapping(method = RequestMethod.GET)
    public String update(ModelMap model) {
        return ClassUtils.getShortName(getEntityClass()).toLowerCase() + "/form";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String save(@ModelAttribute("crudObj") T entity, BindingResult bindingResult) {
        //validate entity
        getValidator().validate(entity, bindingResult);
        
        //return to form if we had errors
        if(bindingResult.hasErrors())
            return ClassUtils.getShortName(getEntityClass()).toLowerCase() + "/form";
        
        //merge into datasource
        dao.save(entity);
        
        //return to list
        return "redirect:/" + ClassUtils.getShortName(getEntityClass()).toLowerCase() + "/list.html";
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public String delete(String isInsert, @ModelAttribute("crudObj") T entity, BindingResult bindingResult) {
        //delete
        dao.delete(entity);
        
        //return to list
        return "redirect:/" + ClassUtils.getShortName(getEntityClass()).toLowerCase() + "/list.html";
    }
    
    @ModelAttribute("crudObj")
    public T setupModel(WebRequest request) {
        String pk = request.getParameter(getPkParam());

        if(pk == null || org.apache.commons.lang.StringUtils.isEmpty(pk)) {
            return getEntityInstance();
        } else {
            return (T)dao.find(getEntityClass(), Long.parseLong(pk));
        }
    }

    //Set up any custom editors, adds a custom one for java.sql.date by default
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomSqlDateEditor(dateFormat, false));
    }*/

}
package com.cloudcode.framework.service;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ServiceResult {
	private int code;
	private String description;
	private Object result;
	private boolean structSupport = true;

	public ServiceResult() {
	}

	public ServiceResult(int code) {
		this(code, null);
	}

	public ServiceResult(int code, String description) {
		this(code, description, null);
	}

	public ServiceResult(int code, String description, Object result) {
		this(code, description, result, true);
	}

	public ServiceResult(int code, Object result) {
		this(code, null, result, true);
	}

	public ServiceResult(int code, String description, Object result,
			boolean structSupport) {
		this.code = code;
		this.description = description;
		this.structSupport = structSupport;
		this.result = cloneResultObject(result, structSupport);
	}
	 public Object cloneResultObject(Object result, boolean structSupport)
	  {
	   // if (BeanUtils.isBasicInstance(result))
	      return result;
	    /*if ((result instanceof Throwable)) {
	      Throwable t = (Throwable)result;
	      return t.getClass().getName() + ":" + t.getMessage();
	    }
	    if ((result instanceof Set)) {
	      Set originalSet = (Set)result;
	      Set clonedSet = new HashSet();
	      for (Iterator i$ = originalSet.iterator(); i$.hasNext(); ) { Object object = i$.next();
	        clonedSet.add(cloneResultObject(object, structSupport));
	      }
	      return clonedSet;
	    }if ((result instanceof List)) {
	      List list0 = (List)result;
	      List list1 = new ArrayList();
	      for (int i = 0; i < list0.size(); i++) {
	        list1.add(cloneResultObject(list0.get(i), structSupport));
	      }
	      return list1;
	    }if (result.getClass().isArray()) {
	      List list = new ArrayList();
	      int i = 0; for (int l = Array.getLength(result); i < l; i++) {
	        list.add(cloneResultObject(Array.get(result, i), structSupport));
	      }
	      return list;
	    }if ((result instanceof Map)) {
	      Map map0 = (Map)result;
	      Map map1 = new HashMap();
	      Iterator iterator = map0.entrySet().iterator();
	      while (iterator.hasNext()) {
	        Map.Entry entry = (Map.Entry)iterator.next();
	        map1.put(entry.getKey(), cloneResultObject(entry.getValue(), structSupport));
	      }
	      return map1;
	    }if (((result instanceof Enum)) || ((result instanceof ResultSet)))
	      return result;
	    if (((result instanceof JSONObject)) || ((result instanceof JSONArray)))
	      return result;
	    try
	    {
	      Object obj = BeanUtils.getClass(result).newInstance();
	      if (!structSupport) {
	        DomainObjectUtils.updateDomainObjectBasePropertiesByAnother(obj, result);
	      } else {
	        String[] fields = AnnotationUtils.getFullInstanceDeclaredEntityFieldNames(obj.getClass());
	        for (String field : fields) {
	          Object val = BeanUtils.getValue(result, field);
	          val = cloneResultObject(val, structSupport);
	          try {
	            BeanUtils.setValue(obj, field, val);
	          } catch (Exception e) {
	            e.printStackTrace();
	          }
	        }
	      }
	      return obj;
	    } catch (Exception e) {
	      LoggerFactory.getLogger(ServiceResult.class).error("clone object error.", e);
	    }return null;*/
	  }

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getResult() {
		return this.result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public boolean isStructSupport() {
		return this.structSupport;
	}

	public void setStructSupport(boolean structSupport) {
		this.structSupport = structSupport;
	}

	public String toString() {
		return new ReflectionToStringBuilder(this,
				ToStringStyle.MULTI_LINE_STYLE).toString();
	}
}

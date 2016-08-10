package com.cloudcode.framework.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Id;

public abstract class AnnotationUtils {
	
	private static Map<Class<?>, Field> primaryKeyMap = new HashMap();
	
	public static final String getPrimaryKeyName(Class<?> type) {
		Field pkField = getPrimaryKeyField(type);
		return pkField == null ? null : pkField.getName();
	}
	public static final Field getPrimaryKeyField(Class<?> type)
	  {
	    if (type == Object.class) {
	      return null;
	    }
	    if (primaryKeyMap.containsKey(type)) {
	      return (Field)primaryKeyMap.get(type);
	    }
	    Field pkField = null;
	    for (Field field : type.getDeclaredFields()) {
	      if ((!Modifier.isStatic(field.getModifiers())) && (field.isAnnotationPresent(Id.class))) {
	        pkField = field;
	        break;
	      }
	    }
	    if ((pkField == null) && (type.getSuperclass() != null) && (type.getSuperclass() != Object.class)) {
	      pkField = getPrimaryKeyField(type.getSuperclass());
	    }
	    primaryKeyMap.put(type, pkField);
	    return pkField;
	  }
}

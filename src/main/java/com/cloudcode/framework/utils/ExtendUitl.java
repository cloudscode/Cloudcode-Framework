package com.cloudcode.framework.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

public class ExtendUitl {
	public static Object processLoadClass2(String classname, String methodname,
			Object[] params) throws Throwable {
		Object o = BeanFactoryHelper.getBeanFactory().getBean(
				Class.forName(classname));

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass(classname);

		Class[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			if (params[i] == null)
				classes[i] = String.class;
			else {
				classes[i] = params[i].getClass();
			}
		}
		Method method = clazz.getMethod(methodname, classes);
		Object _ro = method.invoke(o, params);
		return _ro;
	}

	public static Object getExtendClassResult(String extend, Object o)
			throws Throwable {
		if ((extend != null) && (!"".equals(extend))) {
			String[] paramArr = new String[2];
			paramArr = extend.split("#");
			return processLoadClass(paramArr[0], paramArr[1],
					new Object[] { o });
		}
		return null;
	}

	public static Object getExtendClassResult(String extend) throws Throwable {
		if ((extend != null) && (!"".equals(extend))) {
			String[] paramArr = new String[2];
			paramArr = extend.split("#");
			return processLoadClass(paramArr[0], paramArr[1],
					new Object[] { "" });
		}
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object execute(Object o, String methodname, Object... params) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			Class clazz = loader.loadClass(o.getClass().getName());
			Class[] classes = new Class[params.length];
			for (int i = 0; i < params.length; i++) {
				classes[i] = params[i].getClass();
			}
			Method method = clazz.getMethod(methodname, classes);
			Object _ro = method.invoke(o, params);
			return _ro;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object executeClass(Class classname, String methodname,
			Object... params) throws Throwable {
		Object o = Class.forName(classname.getName()).newInstance();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass(classname.getName());
		Class[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			classes[i] = params[i].getClass();
		}
		Method method = clazz.getMethod(methodname, classes);
		Object _ro = method.invoke(o, params);
		return _ro;
	}

	@SuppressWarnings("rawtypes")
	public static Object executeClassByPropertiesSet(Class classname,
			String propertiesName, Object... params) throws Throwable {
		return executeClassByPropertiesSet(classname,
				"set" + propertiesName.substring(0, 1).toUpperCase()
						+ propertiesName.substring(1), params);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object executeClassReturnObject(Object object, String methodname,
			Object... params) throws Throwable {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass(object.getClass().getName());
		Class[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			classes[i] = params[i].getClass();
		}
		Method method = clazz.getMethod(methodname, classes);
		method.invoke(object, params);
		return object;
	}
	
	public static Object executeClassByPropertiesSet(Object object,
			String propertiesName, Object... params) throws Throwable {
		return executeClassReturnObject(object,
				"set" + propertiesName.substring(0, 1).toUpperCase()
						+ propertiesName.substring(1), params);
	}
	
	public static Object executeClassByPropertiesGet(Object object,
			String propertiesName, Object... params) throws Throwable {
		return executeClassReturnProperties(object,
				"get" + propertiesName.substring(0, 1).toUpperCase()
						+ propertiesName.substring(1), params);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object executeClassReturnProperties(Object object, String methodname,
			Object... params) throws Throwable {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass(object.getClass().getName());
		Class[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			classes[i] = params[i].getClass();
		}
		Method method = clazz.getMethod(methodname, classes);
		return method.invoke(object, params);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object executeSpringClass(String classname,
			String methodname, Object... params) throws Throwable {
		Object o = BeanFactoryHelper.getBeanFactory().getBean(
				Class.forName(classname));
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass(classname);
		Class[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			classes[i] = params[i].getClass();
		}
		Method method = clazz.getMethod(methodname, classes);
		Object _ro = method.invoke(o, params);
		return _ro;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object processLoadClass(String classname, String methodname,
			Object... params) throws Throwable {
		Object o = null;
		Class<?> ccc = Class.forName(classname);
		Annotation[] a = ccc.getAnnotations();
		String value = null;
		for (Annotation annotation : a) {
			if (annotation instanceof Service) {
				org.springframework.stereotype.Service service = (org.springframework.stereotype.Service) annotation;
				value = service.value();
			} else if (annotation instanceof Component) {
				org.springframework.stereotype.Component service = (org.springframework.stereotype.Component) annotation;
				value = service.value();
			} else if (annotation instanceof Repository) {
				org.springframework.stereotype.Repository service = (org.springframework.stereotype.Repository) annotation;
				value = service.value();
			}
		}
		if (Check.isNoEmpty(value)) {
			o = BeanFactoryHelper.getBeanFactory().getBean(value);
		} else {
			o = BeanFactoryHelper.getBeanFactory().getBean(ccc);
		}

		// 通过类装载器获取 类对象
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Class clazz = loader.loadClass(classname);

		// 获取类的默认构造器对象并通过它实例化
		// Constructor cons = clazz.getDeclaredConstructor((Class[])null);
		// T obj = (T) cons.newInstance();

		Class[] classes = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			if (params[i] == null) {
				classes[i] = String.class;
			} else {
				classes[i] = params[i].getClass();
			}
		}
		Method method = clazz.getMethod(methodname, classes);
		Object _ro = method.invoke(o, params);
		return _ro;
	}


	public static Object getImplClassResult(String infStr, Object o)
			throws Throwable {
		if (infStr != null && !"".equals(infStr)) {
			Map<String, Object> map = null;
			String[] paramArr = infStr.split("#");
			if (paramArr.length == 3) {
				try {
					String jsonStr = paramArr[2];
					map = Json.toMap(jsonStr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (map!=null) {
				return ExtendUitl
						.processLoadClass(paramArr[0], paramArr[1], o,map);
			}else{
				return ExtendUitl
						.processLoadClass(paramArr[0], paramArr[1], o);
			}
		}
		return null;
	}


	public static Object getImplClassResult(String infStr) throws Throwable {

		if (infStr != null && !"".equals(infStr)) {
			String[] paramArr = new String[2];
			paramArr = infStr.split("#");
			return ExtendUitl.processLoadClass(paramArr[0], paramArr[1],
					"");
		}
		return null;
	}
}

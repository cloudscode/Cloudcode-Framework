package com.cloudcode.framework.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

import com.cloudcode.framework.model.BaseModelObject;

public class BeanUpdater {

	private static BeanCopier beanCopier = BeanCopier.create(
			BaseModelObject.class, BaseModelObject.class, false);

	public static void updateObject(BaseModelObject modelSource,
			BaseModelObject modelTarget) {
		beanCopier.copy(modelSource, modelTarget, null);
	}

	public static void copyProperties(Object fromObj, Object toObj) {
		Class<? extends Object> fromClass = fromObj.getClass();
		Class<? extends Object> toClass = toObj.getClass();

		try {
			BeanInfo fromBean = Introspector.getBeanInfo(fromClass);
			BeanInfo toBean = Introspector.getBeanInfo(toClass);

			PropertyDescriptor[] toPd = toBean.getPropertyDescriptors();
			List<PropertyDescriptor> fromPd = Arrays.asList(fromBean
					.getPropertyDescriptors());

			for (PropertyDescriptor propertyDescriptor : toPd) {
				propertyDescriptor.getDisplayName();
				PropertyDescriptor pd = fromPd.get(fromPd
						.indexOf(propertyDescriptor));
				if (pd.getDisplayName().equals(
						propertyDescriptor.getDisplayName())
						&& !pd.getDisplayName().equals("class")) {
					if (propertyDescriptor.getWriteMethod() != null)
						propertyDescriptor.getWriteMethod().invoke(toObj,
								pd.getReadMethod().invoke(fromObj, null));
				}

			}
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}

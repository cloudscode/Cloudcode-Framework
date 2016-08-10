package com.cloudcode.framework.utils;

import java.util.HashMap;
import java.util.Map;

import com.cloudcode.framework.annotation.ModelEntity;
import com.cloudcode.framework.bean.ProjectBeanNameGenerator;

public class ModelObjectUtils {
	static Map<Class<?>, String> entityNames = new HashMap();
	static Map<String, Class<?>> entityTypes = new HashMap();

	public static String getEntityName(Class<?> type) {
		String name = (String) entityNames.get(type);
		if (name != null) {
			return name;
		}
		ModelEntity de = (ModelEntity) type.getAnnotation(ModelEntity.class);
		if (de != null) {
			name = de.name();
		} else {
			name = ProjectBeanNameGenerator.getClassProjectName(type.getName());
			if (name == null)
				name = type.getName();
			else {
				name = name + "." + type.getSimpleName();
			}
		}
		entityNames.put(type, name);
		entityTypes.put(name, type);
		return name;
	}
}

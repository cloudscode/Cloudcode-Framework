package com.cloudcode.framework.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import com.cloudcode.framework.annotation.ModuleConfig;

public class ProjectBeanNameGenerator extends AnnotationBeanNameGenerator {
	/*public String generateBeanName(BeanDefinition definition,BeanDefinitionRegistry registry){
		String beanName=super.generateBeanName(definition, registry);
		String projName=getClassProjectName(definition.getBeanClassName());
		if(projName!=null && !beanName.startsWith(projName+"")){
			return projName+"."+beanName;
		}else{
			return beanName;
		}
	}
*/	public static Map<String,String> projectNameMap=new HashMap<String,String>();
	
	public static String getClassProjectName(String className){
		if(className==null || !className.contains(".")){
			return null;
		}
		String packageName=className.replace("\\.[^\\.]+$","");
		String configName=packageName+"."+"ProjectConfig";
		if(projectNameMap.containsKey(configName)){
			return projectNameMap.get(configName);
		}
		try{
			Class<?> configClz=Class.forName(configName);
			ModuleConfig mc=configClz.getAnnotation(ModuleConfig.class);
			String projName=null;
			if(mc!=null){
				projName=mc.name();
			}else{
				projName=(String)configClz.getField("NAME").get(null);
			}
					projectNameMap.put(configName, projName);
					return projName;
		}catch(Exception e){
			return null;//getClassProjectName(packageName);
		}
	}
}

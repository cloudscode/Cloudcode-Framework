package com.cloudcode.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import com.cloudcode.common.cache.CacheManager;
import com.cloudcode.common.cache.MapCacheManagerImpl;
import com.cloudcode.framework.annotation.ModuleConfig;
import com.cloudcode.framework.bean.ProjectBeanNameGenerator;
import com.cloudcode.framework.dao.ModelObjectDao;
import com.cloudcode.framework.dao.impl.BaseDaoImpl;
import com.cloudcode.framework.model.ModelObject;

@ModuleConfig(name=ProjectConfig.NAME,domainPackages={"com.cloudcode.framework.model"})
@ComponentScan(basePackages={"com.cloudcode.framework.*"},nameGenerator=ProjectBeanNameGenerator.class)
@PropertySource(name="cloudcode.evn",value={"classpath:proj.properties"})
public class ProjectConfig {
	public static final String NAME="cloudcode";
	public static final String PREFIX=NAME+".";
	
	
	@Bean(name=PREFIX+"modelDao")
	public ModelObjectDao<ModelObject> generateModelDao(){
		return new BaseDaoImpl<ModelObject>(ModelObject.class);
	}
   @Bean(name={"global.cacheManager"})
   CacheManager globalCacheManager() {
     return new MapCacheManagerImpl();
   }
}

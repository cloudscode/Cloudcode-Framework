package com.cloudcode.framework.init;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cloudcode.framework.utils.Initializer;
import com.cloudcode.framework.utils.statics.StaticVar;

@Component("initsystem")
public class init implements Initializer{
	private static final Logger logger = Logger
			.getLogger(init.class);
	
	@PostConstruct
	public void initialize() {
		try {
			this.initProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void initProperties() throws IOException {
		String classPath = "/"+Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		classPath = classPath.substring(1, classPath.indexOf("/WEB-INF"));
		StaticVar.contextPath =  classPath;
		logger.info("项目绝对路径：" + StaticVar.contextPath);
		classPath = classPath.substring(0, classPath.lastIndexOf("/"));
		StaticVar.webappPath = classPath;
		logger.info("web服务器绝对路径：" + StaticVar.webappPath);
		StaticVar.filepath = StaticVar.webappPath + StaticVar.filebasepath;
		logger.info("附件绝对路径：" + StaticVar.filepath);
		//FileUtil.isExist(StaticVar.filepath);
		//FileUtil.isExist(StaticVar.filepath + "/task");
	}
}

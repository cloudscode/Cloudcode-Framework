package com.cloudcode.framework.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtil {
	private static Configuration cfg = null;
	static {
		cfg = new Configuration();
		URL url = FreeMarkerUtil.class.getClassLoader().getResource("\\com\\cloudcode\\ftl\\");
		File dir = new File(url.getFile());
		try {
			cfg.setDirectoryForTemplateLoading(dir);//new File("src\\main\\java\\com\\cloudcode\\ftl\\")
			cfg.setDefaultEncoding("UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String process(Map<String, String> rootMap,
			String templateName) {

		Template template;
		StringWriter out = new StringWriter(2048);
		try {
			template = cfg.getTemplate(templateName);
			template.process(rootMap, out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return out.toString();
	}
}
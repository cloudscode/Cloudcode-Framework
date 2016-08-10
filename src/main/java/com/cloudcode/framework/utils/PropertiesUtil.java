package com.cloudcode.framework.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesUtil {
	private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
	private String propertyName = "sys.properties";

	public PropertiesUtil() {
	}

	public PropertiesUtil(String fileName) {
		if (null != fileName && "" != fileName) {
			this.propertyName = fileName;
		}
	}

	public String readProperty(String key) {
		String value = "";
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(
					this.propertyName);
			Properties p = new Properties();
			p.load(is);
			value = p.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("【PropertiesUtil读取】" + e.getMessage());
			IOUtils.close(is);
		} finally {
			IOUtils.close(is);
		}
		return value;
	}

	public Properties getProperties() {
		Properties p = new Properties();
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(
					this.propertyName);
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("【PropertiesUtil获取】" + e.getMessage());
			IOUtils.close(is);
		} finally {
			IOUtils.close(is);
		}
		return p;
	}

	public void writeProperty(String key, String value) {
		InputStream is = null;
		OutputStream os = null;
		Properties p = new Properties();
		try {
			is = new FileInputStream(this.propertyName);
			p.load(is);
			os = new FileOutputStream(PropertiesUtil.class.getClassLoader()
					.getResource(this.propertyName).getFile());

			p.setProperty(key, value);
			p.store(os, key);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("【PropertiesUtil写入】" + e.getMessage());
			IOUtils.close(is);
			IOUtils.close(os);
		} finally {
			IOUtils.close(is);
			IOUtils.close(os);
		}
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtil.class.getClassLoader()
				.getResource("").getPath());
		PropertiesUtil p = new PropertiesUtil("");
		System.out.println(p.readProperty(""));
	}
}

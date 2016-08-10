package com.cloudcode.framework.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

@Service
public class BeanFactoryHelper implements BeanFactoryAware {

	private static BeanFactory factory;
	
	public void setBeanFactory(BeanFactory arg0) throws BeansException {
		factory = arg0;
	}
	public static BeanFactory getBeanFactory(){
		return factory;
	}
}

/**
 * Project Name:Cloudcode-Framework
 * File Name:EhCacheFactoryImpl.java
 * Package Name:com.cloudcode.common.cache
 * Date:2016-10-13下午1:55:21
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.cloudcode.common.cache;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.ResourceUtils;

public class EhCacheManagerImpl implements CacheManager {
	private net.sf.ehcache.CacheManager cacheManager = getCacheManager();

	public Cache<Object, Object> getCache(String name) {
		if (!this.cacheManager.cacheExists(name)) {
			this.cacheManager.addCache(name);
		}
		net.sf.ehcache.Cache ehcache = this.cacheManager.getCache(name);
		return new EhCacheImpl<Object, Object>(ehcache);
	}

	public <K, V> Cache<K, V> getCache(String name, Class<K> keyType,
			Class<V> valueType) {
		if (!this.cacheManager.cacheExists(name)) {
			this.cacheManager.addCache(name);
		}
		net.sf.ehcache.Cache ehcache = this.cacheManager.getCache(name);
		return new EhCacheImpl<K, V>(ehcache);
	}

	public List<Cache<?, ?>> allCaches() {
		List<Cache<?, ?>> list = new ArrayList<Cache<?, ?>>();
		for (String name : this.cacheManager.getCacheNames())
			if (this.cacheManager.cacheExists(name)) {
				net.sf.ehcache.Cache ehcache = this.cacheManager.getCache(name);
				list.add(new EhCacheImpl<Object, Object>(ehcache));
			}
		return list;
	}

	net.sf.ehcache.CacheManager getCacheManager() {
		try {
			URL url = ResourceUtils.getURL("classpath:/ehcache.xml");
			return net.sf.ehcache.CacheManager.create(url);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}

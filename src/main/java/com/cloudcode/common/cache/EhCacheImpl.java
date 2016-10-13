/**
 * Project Name:Cloudcode-Framework
 * File Name:EhCacheImpl.java
 * Package Name:com.cloudcode.common.cache
 * Date:2016-10-13下午1:53:31
 * Copyright (c) 2016, chenzhou1025@126.com All Rights Reserved.
 *
 */

package com.cloudcode.common.cache;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.ehcache.Element;

public class EhCacheImpl<K, V> implements Cache<K, V> {
	protected net.sf.ehcache.Cache delegate;

	public EhCacheImpl(net.sf.ehcache.Cache cache) {
		this.delegate = cache;
	}
	@SuppressWarnings("unchecked")
	public V get(K key) {
		Element el = delegate.get(key);
		return el == null ? null :(V) el.getValue();
	}

	public void put(K key, V value) {
		Element el = new Element(key, value);
		delegate.put(el);
	}

	public void clear() {
		delegate.removeAll();
	}

	public boolean isIn(K key) {
		return delegate.isKeyInCache(key);
	}

	public int getSize() {
		return delegate.getSize();
	}

	public void remove(K key) {
		delegate.remove(key);
	}
	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		Collection<V> list = new ArrayList<V>();
		for (Object key:delegate.getKeys()) {
			list.add((V)delegate.get(key));
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public Collection<K> keys() {
		return delegate.getKeys();
	}
}

package com.cloudcode.common.cache;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class MapCacheImpl<K, V>implements Cache<K, V>
{
  private Map<K, V> current = Maps.newConcurrentMap();

  public V get(K key) {
    synchronized (this.current) {
      return this.current.get(key);
    }
  }

  public void put(K key, V value) {
    synchronized (this.current) {
      this.current.put(key, value);
    }
  }

  public void clear() {
    synchronized (this.current) {
      this.current.clear();
    }
  }

  public void remove(K key) {
    synchronized (this.current) {
      this.current.remove(key);
    }
  }

  public int getSize() {
    return this.current.size();
  }

  public boolean isIn(K key) {
    return this.current.containsKey(key);
  }

  public Collection<K> keys() {
    return this.current.keySet();
  }

  public Collection<V> values() {
    return this.current.values();
  }
}
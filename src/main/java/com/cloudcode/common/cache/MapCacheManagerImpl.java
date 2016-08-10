package com.cloudcode.common.cache;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

public class MapCacheManagerImpl implements CacheManager
{
  private Map<String, MapCacheImpl<?, ?>> current = Maps.newConcurrentMap();

  public Cache<Object, Object> getCache(String name)
  {
    synchronized (this.current) {
      MapCacheImpl<Object, Object> cache = (MapCacheImpl<Object, Object>)this.current.get(name);
      if (cache == null) {
        cache = new MapCacheImpl<Object, Object>();
        this.current.put(name, cache);
      }
      return cache;
    }
  }

  public <K, V> Cache<K, V> getCache(String name, Class<K> keyType, Class<V> valueType)
  {
    synchronized (this.current) {
      MapCacheImpl<K, V> cache = (MapCacheImpl<K, V>)this.current.get(name);
      if (cache == null) {
        cache = new MapCacheImpl<K, V>();
        this.current.put(name, cache);
      }
      return (Cache<K, V>) cache;
    }
  }

  public Collection<Cache<?, ?>> allCaches() {
    return new ArrayList(this.current.values());
  }
}
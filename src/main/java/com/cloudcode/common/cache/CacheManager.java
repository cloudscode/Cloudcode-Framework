package com.cloudcode.common.cache;

import java.util.Collection;

public abstract interface CacheManager
{
  public abstract Cache<Object, Object> getCache(String paramString);

  public abstract <K, V> Cache<K, V> getCache(String paramString, Class<K> paramClass, Class<V> paramClass1);

  public abstract Collection<Cache<?, ?>> allCaches();
  
}

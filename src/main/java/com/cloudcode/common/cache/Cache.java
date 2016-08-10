package com.cloudcode.common.cache;

import java.util.Collection;

public abstract interface Cache<K, V>
{
  public abstract V get(K paramK);

  public abstract void put(K paramK, V paramV);

  public abstract void remove(K paramK);

  public abstract Collection<K> keys();

  public abstract Collection<V> values();

  public abstract boolean isIn(K paramK);

  public abstract int getSize();

  public abstract void clear();
  
}

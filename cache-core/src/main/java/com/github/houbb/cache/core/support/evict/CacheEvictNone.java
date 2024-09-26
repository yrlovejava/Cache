package com.github.houbb.cache.core.support.evict;

import com.github.houbb.cache.api.ICacheEvict;
import com.github.houbb.cache.api.ICacheEvictContext;

/**
 * 丢弃策略-不丢弃
 * @param <K>
 * @param <V>
 */
public class CacheEvictNone<K,V> implements ICacheEvict<K,V> {

    @Override
    public void evict(ICacheEvictContext<K, V> context) {

    }
}

package com.github.houbb.cache.api;

/**
 * 驱逐策略的接口
 * @param <K>
 * @param <V>
 */
public interface ICacheEvict<K,V> {

    /**
     * 驱逐策略
     * @param context 上下文
     * @param context
     */
    void evict(final ICacheEvictContext<K,V> context);
}

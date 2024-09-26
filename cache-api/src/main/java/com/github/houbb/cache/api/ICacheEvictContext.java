package com.github.houbb.cache.api;

/**
 * 驱逐策略
 *
 * 1.新加的key
 * 2.map实现
 * 3.淘汰监听
 * @param <K> key
 * @param <V> value
 */
public interface ICacheEvictContext<K,V> {

    /**
     * 新加的key
     * @return
     */
    K key();

    /**
     * cache实现
     * @return
     */
    ICache<K,V> cache();

    /**
     * 获取大小
     * @return
     */
    int size();
}

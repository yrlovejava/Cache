package com.github.houbb.cache.api;

import java.util.Map;

/**
 * 缓存上下文的接口
 * @param <K>
 * @param <V>
 */
public interface ICacheContext<K,V> {

    /**
     * map信息
     * @return
     */
    Map<K,V> map();

    /**
     * 大小限制
     * @return
     */
    int size();

    /**
     * 驱逐策略
     * @return
     */
    ICacheEvict<K,V> cacheEvict();
}

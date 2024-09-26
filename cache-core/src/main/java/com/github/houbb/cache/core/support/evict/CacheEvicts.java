package com.github.houbb.cache.core.support.evict;

import com.github.houbb.cache.api.ICacheEvict;

/**
 * 丢弃策略的集合
 * 方便外部调用
 */
public final class CacheEvicts {

    private CacheEvicts() {

    }

    /**
     * 无策略
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     */
    public static <K, V> ICacheEvict<K, V> none() {
        return new CacheEvictNone<>();
    }

    /**
     * 先进先出
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     */
    public static <K, V> ICacheEvict<K, V> fifo() {
        return new CacheEvictFIFO<>();
    }
}

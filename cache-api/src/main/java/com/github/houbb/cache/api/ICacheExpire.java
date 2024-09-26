package com.github.houbb.cache.api;

import java.util.Collection;

/**
 * 缓存过期接口
 *
 * @param <K>
 * @param <V>
 */
public interface ICacheExpire<K, V> {

    /**
     * 指定过期信息
     *
     * @param key      key
     * @param expireAt 过期时间戳
     */
    void expire(final K key, final long expireAt);

    /**
     * 惰性删除中需要处理的 keys
     * 惰性删除，即需要刷新时才考虑
     * @param keyList
     */
    void refreshExpire(final Collection<K> keyList);
}

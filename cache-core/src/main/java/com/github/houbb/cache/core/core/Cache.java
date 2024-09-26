package com.github.houbb.cache.core.core;

import com.github.houbb.cache.api.ICache;
import com.github.houbb.cache.api.ICacheContext;
import com.github.houbb.cache.api.ICacheEvict;
import com.github.houbb.cache.api.ICacheExpire;
import com.github.houbb.cache.core.support.evict.CacheEvictContext;
import com.github.houbb.cache.core.exception.CacheRuntimeException;
import com.github.houbb.cache.core.support.expire.CacheExpire;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存信息
 * @param <K> key
 * @param <V> value
 */
public class Cache<K,V> implements ICache<K,V> {

    /**
     * map 信息
     */
    private final Map<K,V> map;
    /**
     * 大小限制
     */
    private final int sizeLimit;
    /**
     * 驱除策略
     */
    private final ICacheEvict<K,V> cacheEvict;
    /**
     * 过期策略
     */
    private final ICacheExpire<K,V> cacheExpire;

    public Cache(ICacheContext<K, V> context) {
        this.map = context.map();
        this.sizeLimit = context.size();
        this.cacheEvict = context.cacheEvict();
        this.cacheExpire = new CacheExpire<>(this);
    }

    @Override
    public ICache<K, V> expire(K key, long expireTime, TimeUnit timeUnit) {
        // 将过期改写为在当前时间+expireTime之后定时过期
        long timestamp = System.currentTimeMillis() + timeUnit.toMillis(expireTime);
        return this.expireAt(key,timestamp);
    }

    @Override
    public ICache<K, V> expireAt(K key, long timestamp) {
        this.cacheExpire.expire(key,timestamp);
        return this;
    }

    @Override
    public int size() {
        // 1.刷新所有过期信息，因为是惰性删除，所以就算过期了，如果没有使用，也不会删除
        this.refreshExpireAllKeys();

        return map.size();
    }

    @Override
    public boolean isEmpty() {
        // 1.刷新所有过期信息，因为是惰性删除，所以就算过期了，如果没有使用，也不会删除
        this.refreshExpireAllKeys();

        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        // 1.刷新所有过期信息，因为是惰性删除，所以就算过期了，如果没有使用，也不会删除
        this.refreshExpireAllKeys();

        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        // 1.刷新所有过期信息，因为是惰性删除，所以就算过期了，如果没有使用，也不会删除
        this.refreshExpireAllKeys();

        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        // 1.刷新所有过期信息，因为是惰性删除，所以就算过期了，如果没有使用，也不会删除
        K genericKey = (K) key;
        this.cacheExpire.refreshExpire(Collections.singletonList(genericKey));

        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        // 1.1创建驱逐策略
        CacheEvictContext<K,V> context = new CacheEvictContext<>();
        context.key(key).size(sizeLimit).cache(this);
        cacheEvict.evict(context);

        //2. 判断驱除后的信息
        if(isSizeLimit()) {
            throw new CacheRuntimeException("当前队列已满，数据添加失败！");
        }
        //3. 执行添加
        return map.put(key, value);
    }

    /**
     * 是否已经达到大小最大的限制
     * @return 是否限制
     */
    private boolean isSizeLimit() {
        final int currentSize = this.size();
        return currentSize >= this.sizeLimit;
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Set<K> keySet() {
        this.refreshExpireAllKeys();

        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        this.refreshExpireAllKeys();

        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        this.refreshExpireAllKeys();

        return map.entrySet();
    }

    /**
     * 刷新懒过期的所有keys
     */
    private void refreshExpireAllKeys() {
        this.cacheExpire.refreshExpire(map.keySet());
    }
}

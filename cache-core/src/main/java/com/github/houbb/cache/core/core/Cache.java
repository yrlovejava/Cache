package com.github.houbb.cache.core.core;

import com.github.houbb.cache.api.ICache;
import com.github.houbb.cache.api.ICacheContext;
import com.github.houbb.cache.api.ICacheEvict;
import com.github.houbb.cache.core.support.evict.CacheEvictContext;
import com.github.houbb.cache.core.exception.CacheRuntimeException;

import java.util.Collection;
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

    public Cache(ICacheContext<K, V> context) {
        this.map = context.map();
        this.sizeLimit = context.size();
        this.cacheEvict = context.cacheEvict();
    }

    @Override
    public ICache<K, V> expire(K key, long expireTime, TimeUnit timeUnit) {
        // 先设置为不支持
        throw new UnsupportedOperationException();
    }

    @Override
    public ICache<K, V> expireAt(K key, long timestamp) {
        // 先设置为不支持
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
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
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}

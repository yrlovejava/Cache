package com.github.houbb.cache.core.api;

import com.github.houbb.cache.api.ICache;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存适配器
 */
public class CacheAdaptor<K,V> implements ICache<K, V> {

    /**
     * 设置过期时间
     * @param key        key
     * @param expireTime 过期时间
     * @param timeUnit   时间单位
     * @return
     */
    @Override
    public ICache<K, V> expire(K key, long expireTime, TimeUnit timeUnit) {
        return null;
    }

    /**
     * 在指定时间过期
     * @param key       key
     * @param timestamp 时间戳
     * @return
     */
    @Override
    public ICache<K, V> expireAt(K key, long timestamp) {
        return null;
    }

    /**
     * 重写Map接口的size方法
     * @return
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * 重写Map的isEmpty方法
     * @return
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * 重写Map的判断key是否存在方法
     * @param key key whose presence in this map is to be tested
     * @return
     */
    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    /**
     * 重写Map的判断值是否存在方法
     * @param value value whose presence in this map is to be tested
     * @return
     */
    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    /**
     * 重写Map的根据key获取值方法
     * @param key the key whose associated value is to be returned
     * @return
     */
    @Override
    public V get(Object key) {
        return null;
    }

    /**
     * 重写Map的插入方法
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return
     */
    @Override
    public V put(K key, V value) {
        return null;
    }

    /**
     * 重写Map的删除方法
     * @param key key whose mapping is to be removed from the map
     * @return
     */
    @Override
    public V remove(Object key) {
        return null;
    }

    /**
     * 重写Map的批量插入方法
     * @param m mappings to be stored in this map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    /**
     * 重写Map的清理方法
     */
    @Override
    public void clear() {

    }

    /**
     * 重写Map的获取所有key的方法
     * @return
     */
    @Override
    public Set<K> keySet() {
        return Collections.emptySet();
    }

    /**
     * 重写Map的获取所有value方法
     * @return
     */
    @Override
    public Collection<V> values() {
        return Collections.emptyList();
    }

    /**
     * 重写Map的获取所有k，v键值对的方法
     * @return
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        return Collections.emptySet();
    }
}

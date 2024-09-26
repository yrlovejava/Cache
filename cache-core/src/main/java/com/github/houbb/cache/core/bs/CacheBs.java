package com.github.houbb.cache.core.bs;

import com.github.houbb.cache.api.ICache;
import com.github.houbb.cache.api.ICacheEvict;

import com.github.houbb.cache.core.core.Cache;
import com.github.houbb.cache.core.core.CacheContext;
import com.github.houbb.cache.core.support.evict.CacheEvicts;
import com.github.houbb.heaven.util.common.ArgUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 缓存引导类
 *
 * @param <K>
 * @param <V>
 */
public class CacheBs<K, V> {

    private CacheBs() {

    }

    /**
     * 创建对象实例
     *
     * @param <K> key
     * @param <V> value
     * @return
     */
    public static <K, V> CacheBs<K, V> newInstance() {
        return new CacheBs<K, V>();
    }

    /**
     * map作为缓存
     */
    private Map<K,V> map = new HashMap<>();

    /**
     * 大小限制
     */
    private int size = Integer.MAX_VALUE;

    /**
     * 驱逐策略 先进先出
     */
    private ICacheEvict<K,V> evict = CacheEvicts.fifo();

    /**
     * 设置缓存的map
     * @param map 用做缓存的map
     * @return this
     */
    public CacheBs<K,V> map(Map<K,V> map){
        // 判断参数不能为空, name参数用作提示方法
        ArgUtil.notNull(map,"map");

        // 使用自定义的map
        this.map = map;

        return this;
    }

    /**
     * 设置size信息
     * @param size 大小
     * @return this
     */
    public CacheBs<K,V> size(int size){
        // 判断size不能为负数
        ArgUtil.notNegative(size,"size");

        this.size = size;
        return this;
    }

    /**
     * 设置驱除策略
     * @param evict 驱除策略
     * @return this
     */
    public CacheBs<K, V> evict(ICacheEvict<K, V> evict) {
        this.evict = evict;
        return this;
    }

    /**
     * 构建缓存信息
     * @return 缓存信息
     * @since 0.0.2
     */
    public ICache<K,V> build() {
        CacheContext<K,V> context = new CacheContext<>();
        context.cacheEvict(evict);
        context.map(map);
        context.size(size);
        return new Cache<>(context);
    }
}

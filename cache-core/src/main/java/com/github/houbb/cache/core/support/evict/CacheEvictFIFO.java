package com.github.houbb.cache.core.support.evict;

import com.github.houbb.cache.api.ICache;
import com.github.houbb.cache.api.ICacheEvict;
import com.github.houbb.cache.api.ICacheEvictContext;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 丢弃策略-先进先出
 * @param <K>
 * @param <V>
 */
public class CacheEvictFIFO<K,V> implements ICacheEvict<K,V> {

    /**
     * queue信息，用双向链表保证顺序
     */
    private Queue<K> queue = new LinkedList<>();

    @Override
    public void evict(ICacheEvictContext<K, V> context) {
        // 1.获取缓存信息
        final ICache<K,V> cache = context.cache();
        // 2.判断是否超过限制
        if(cache.size() >= context.size()){
            // 超过限制，执行移除
            K evictKey = queue.remove();
            // 移除最开始的元素
            cache.remove(evictKey);
        }

        // 将新加的元素放入队尾
        final K key = context.key();
        queue.add(key);
    }
}

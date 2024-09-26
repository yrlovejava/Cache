package com.github.houbb.cache.core.support.expire;

import com.github.houbb.cache.api.ICache;
import com.github.houbb.cache.api.ICacheExpire;
import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.heaven.util.util.MapUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 缓存过期-普通策略
 * @param <K> key
 * @param <V> value
 */
public class CacheExpire<K,V> implements ICacheExpire<K,V> {

    /**
     * 单次清空的数量限制
     */
    private static final int LIMIT = 100;

    /**
     * 过期 map
     *
     * 空间换时间
     */
    private final Map<K,Long> expireMap = new HashMap<>();

    /**
     * 缓存实现
     */
    private final ICache<K,V> cache;

    /**
     * 线程执行类
     */
    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor();

    public CacheExpire(ICache<K,V> cache){
        this.cache = cache;
        this.init();
    }

    /**
     * 初始化任务
     */
    private void init() {
        EXECUTOR_SERVICE.scheduleAtFixedRate(new ExpireThread(), 100, 100, TimeUnit.MICROSECONDS);
    }

    private class ExpireThread implements Runnable{

        @Override
        public void run() {
            // 1.判断是否为空
            if(MapUtil.isEmpty(expireMap)){
                return;
            }

            // 2.获取key进行处理
            int count = 0;
            for (Map.Entry<K,Long> entry : expireMap.entrySet()){
                if(count >= LIMIT){
                    return;
                }

                expireKey(entry);
                count++;
            }
        }
    }


    @Override
    public void expire(K key, long expireAt) {
        expireMap.put(key,expireAt);
    }

    @Override
    public void refreshExpire(Collection<K> keyList) {
        if(CollectionUtil.isEmpty(keyList)){
            return;
        }

        // 判断大小，小的作为外循环，一般都是过期的keys比较小
        if(keyList.size() <= expireMap.size()){
            for(K key : keyList){
                expireKey(key);
            }
        }else {
            for(Map.Entry<K, Long> entry : expireMap.entrySet()) {
                this.expireKey(entry);
            }
        }
    }

    /**
     * 执行过期操作
     * @param entry entry
     */
    private void expireKey(Map.Entry<K, Long> entry) {
        final K key = entry.getKey();
        final Long expireAt = entry.getValue();

        // 删除的逻辑处理
        long currentTime = System.currentTimeMillis();
        if(currentTime >= expireAt){
            // 先从过期表中移除
            expireMap.remove(key);

            // 再删除缓存，后续可以通过惰性删除做补偿
            cache.remove(key);
        }
    }

    /**
     * 过期处理key
     * @param key key
     */
    private void expireKey(final K key){
        // 获取过期的时间戳
        Long expireAt = expireMap.get(key);
        if(expireAt == null){
            return;
        }

        long currentTime = System.currentTimeMillis();
        if(currentTime >= expireAt){
            expireMap.remove(key);

            cache.remove(key);
        }
    }
}

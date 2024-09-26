package com.github.houbb.cache.core.support.map;

import java.util.HashMap;
import java.util.Map;

public final class Maps {

    private Maps() {}

    /**
     * hashMap实现策略
     * @return
     * @param <K>
     * @param <V>
     */
    public static <K,V> Map<K,V> hashMap() {
        return new HashMap<>();
    }
}

package com.github.houbb.cache.core.exception;

/**
 * 缓存运行时异常
 */
public class CacheRuntimeException extends RuntimeException {

    public CacheRuntimeException() {
    }
    public CacheRuntimeException(String message) {
        super(message);
    }
    public CacheRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
    public CacheRuntimeException(Throwable cause) {
        super(cause);
    }
    public CacheRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

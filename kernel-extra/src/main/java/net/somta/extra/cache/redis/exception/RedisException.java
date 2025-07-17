package net.somta.extra.cache.redis.exception;

import net.somta.core.exception.SysException;
import net.somta.core.base.IBaseError;

/**
 * Redis自定义异常
 */
public class RedisException extends SysException {

    public RedisException(IBaseError baseError, Object... args) {
        super(baseError,args);
    }

    public RedisException(IBaseError baseError, Throwable throwable, Object... args) {
        super(baseError,throwable,args);
    }
}
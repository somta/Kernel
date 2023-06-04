package net.somta.core.cache.redis.exception;

import net.somta.core.base.IBaseError;
import net.somta.core.exception.SysException;
/**
 * Redis自定义异常
 * @author: husong
 **/
public class RedisException extends SysException {
    public RedisException(IBaseError baseError, Object... args) {
        super(baseError,args);
    }

    public RedisException(IBaseError baseError, Throwable throwable, Object... args) {
        super(baseError,throwable,args);
    }
}
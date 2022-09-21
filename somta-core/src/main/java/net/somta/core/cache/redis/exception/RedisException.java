package net.somta.core.cache.redis.exception;

import net.somta.core.base.IBaseError;
import net.somta.core.exception.SysException;
/**
 * @desc: Redis自定义异常
 * @author: husong
 * @date: 2022/7/12
 **/
public class RedisException extends SysException {
    public RedisException(IBaseError baseError, Object... args) {
        super(baseError,args);
    }

    public RedisException(IBaseError baseError, Throwable throwable, Object... args) {
        super(baseError,throwable,args);
    }
}
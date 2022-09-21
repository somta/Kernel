package net.somta.core.cache.redis.model;

import net.somta.core.contants.SystemContants;
import net.somta.core.base.IBaseError;

/**
 * @desc: Redis异常枚举
 * @author: husong
 * @date: 2022/7/15
 **/
public enum RedisErrorEnum implements IBaseError {

    REDIS_MODE_ERROR(1001,  "不存在%s这种Redis的模式"),
    REDIS_ADDRESS_ERROR(1002,  "Redis的地址不能为空"),
    REDIS_SENTINEL_MASTER_NAME_ERROR(1003,  "哨兵模式下主服务名称不能为空"),;

    private int errorCode;
    private String errorMsg;

    RedisErrorEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public long getErrorCode() {
        return SystemContants.CORE_CODE + errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}

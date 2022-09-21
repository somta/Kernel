package net.somta.core.enums;

import net.somta.core.contants.SystemContants;
import net.somta.core.base.IBaseError;

/**
 * @desc: 核心系统最基础的异常枚举，以0001 - 1000
 * @author: husong
 * @date: 2022/7/13
 **/
public enum SystemErrorEnum implements IBaseError {
    BASE_ADD_ERROR(0001,  "新增数据失败"),
    BASE_DELETE_BY_ID_ERROR(0002,  "根据ID删除数据失败"),
    BASE_UPDATE_ERROR(0003,  "修改数据失败"),
    SERIALIZE_ERROR(0004,"序列化异常"),
    DSERIALIZE_ERROR(0005,"反序列化异常");

    private int errorCode;
    private String errorMsg;

    SystemErrorEnum(int errorCode, String errorMsg) {
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

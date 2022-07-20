package net.somta.core.enums;

import net.somta.core.exception.IBaseError;

public enum SystemErrorEnum implements IBaseError {
    BASE_ADD_ERROR(100103,  "新增数据失败"),
    BASE_DELETE_BY_ID_ERROR(100104,  "根据ID删除数据失败"),
    BASE_UPDATE_ERROR(100105,  "修改数据失败"),
    SERIALIZE_ERROR(0001,"序列化异常"),
    DSERIALIZE_ERROR(0002,"反序列化异常");

    private int errorCode;
    private String errorMessage;

    SystemErrorEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public long getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}

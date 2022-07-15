package net.somta.demo.enums;

import net.somta.core.exception.IBaseError;

public enum CommonErrorEnum implements IBaseError {
    BIZ_ERROR(200103,  "这是一个业务异常"),
    SYS_ERROR(200403,  "这是一个系统异常");

    private int errorCode;
    private String errorMessage;

    CommonErrorEnum(int errorCode, String errorMessage) {
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

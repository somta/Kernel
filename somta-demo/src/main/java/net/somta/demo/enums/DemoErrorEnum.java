package net.somta.demo.enums;

import net.somta.core.exception.IBaseError;

public enum DemoErrorEnum implements IBaseError {
    BIZ_ERROR(2000003,  "这是一个业务异常"),
    SYS_ERROR(2000004,  "这是一个系统异常");

    private int errorCode;
    private String errorMessage;

    DemoErrorEnum(int errorCode, String errorMessage) {
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

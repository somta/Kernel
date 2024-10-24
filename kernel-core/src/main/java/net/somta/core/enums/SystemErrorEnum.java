package net.somta.core.enums;

import net.somta.core.contants.SystemContants;
import net.somta.core.base.IBaseError;

/**
 * 核心系统最基础的异常枚举，以0001 - 1000
 * @author: husong
 **/
public enum SystemErrorEnum implements IBaseError {

    ;
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

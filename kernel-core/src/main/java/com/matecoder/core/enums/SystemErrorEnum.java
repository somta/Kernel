package com.matecoder.core.enums;

import com.matecoder.core.base.IBaseError;
import com.matecoder.core.constants.SystemConstants;

/**
 * 核心系统最基础的异常枚举，以0001 - 1000
 * @author husong
 **/
public enum SystemErrorEnum implements IBaseError {

    ;
    /**
     * 错误码
     */
    private final int errorCode;
    /**
     * 错误信息
     */
    private final String errorMsg;

    SystemErrorEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public long getErrorCode() {
        return SystemConstants.CORE_CODE + errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}

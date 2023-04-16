package net.somta.common.enums;

import net.somta.core.contants.SystemContants;
import net.somta.core.base.IBaseError;

/**
 * 公共错误枚举类
 * @author husong
 * @date 2022/7/14
 **/
public enum CommonErrorEnum implements IBaseError {
    SNOW_FLAKE_ERROR(1001,  "雪花ID错误，系统时钟回退了"),;

    private int errorCode;
    private String errorMsg;

    CommonErrorEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public long getErrorCode() {
        return SystemContants.COMMON_CODE + errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }
}

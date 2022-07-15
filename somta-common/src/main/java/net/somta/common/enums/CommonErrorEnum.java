package net.somta.common.enums;

import net.somta.core.contants.SystemContants;
import net.somta.core.exception.IBaseError;

/**
 * @desc: TODO 类描述
 * @author: mijunjie
 * @date: 2022/7/14
 **/
public enum CommonErrorEnum implements IBaseError {
    SERIALIZE_ERROR(0001,"序列化异常"),
    DSERIALIZE_ERROR(0002,"反序列化异常");

    private long code;
    private String errorMsg;

    private int errorCode;
    private String errorMessage;

    CommonErrorEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    @Override
    public long getErrorCode() {
        return SystemContants.COMMON_CODE + errorCode;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }
}

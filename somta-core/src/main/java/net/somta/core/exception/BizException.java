package net.somta.core.exception;

public class BizException extends BaseException {

    public BizException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage, ExceptionConstants.ERROR_TYPE_BIZ);
    }

}

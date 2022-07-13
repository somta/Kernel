package net.somta.core.exception;

public class BizException extends BaseException {

    public BizException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage, ExceptionConstants.ERROR_TYPE_BIZ);
    }

    public BizException(IBaseError baseError) {
        super(baseError.getErrorCode(), baseError.getErrorMessage(), ExceptionConstants.ERROR_TYPE_BIZ);
    }

}

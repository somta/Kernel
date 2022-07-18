package net.somta.core.exception;

public class BizException extends BaseException {

    public BizException(IBaseError baseError,Object... args) {
        super(baseError.getErrorCode(), String.format(baseError.getErrorMessage(),args), ExceptionConstants.ERROR_TYPE_BIZ);
    }

    public BizException(IBaseError baseError,Throwable throwable,Object... args) {
        super(baseError.getErrorCode(), String.format(baseError.getErrorMessage(),args), ExceptionConstants.ERROR_TYPE_BIZ,throwable);
    }

}

package net.somta.core.exception;

import net.somta.core.base.IBaseError;

public class BizException extends BaseException {

    public BizException(IBaseError baseError, Object... args) {
        super(baseError.getErrorCode(), String.format(baseError.getErrorMsg(),args), ExceptionConstants.ERROR_TYPE_BIZ);
    }

    public BizException(IBaseError baseError,Throwable throwable,Object... args) {
        super(baseError.getErrorCode(), String.format(baseError.getErrorMsg(),args), ExceptionConstants.ERROR_TYPE_BIZ,throwable);
    }

    public BizException(long errorCode,IBaseError baseError,Object... args) {
        super(errorCode, String.format(baseError.getErrorMsg(),args), ExceptionConstants.ERROR_TYPE_BIZ);
    }
}

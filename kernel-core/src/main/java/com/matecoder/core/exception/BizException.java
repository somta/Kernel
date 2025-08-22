package com.matecoder.core.exception;

import com.matecoder.core.base.IBaseError;

/**
 * 业务异常类
 * @author husong
 */
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

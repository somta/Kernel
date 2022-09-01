package net.somta.core.exception;

public class SysException extends BaseException {

    public SysException(IBaseError baseError,Object... args) {
        super(baseError.getErrorCode(), String.format(baseError.getErrorMsg(),args), ExceptionConstants.ERROR_TYPE_SYS);
    }

    public SysException(IBaseError baseError,Throwable throwable,Object... args) {
        super(baseError.getErrorCode(), String.format(baseError.getErrorMsg(),args), ExceptionConstants.ERROR_TYPE_SYS,throwable);
    }

    public SysException(long errorCode,IBaseError baseError,Throwable throwable,Object... args) {
        super(errorCode, String.format(baseError.getErrorMsg(),args), ExceptionConstants.ERROR_TYPE_SYS,throwable);
    }

}

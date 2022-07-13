package net.somta.core.exception;

public class SysException extends BaseException {

    public SysException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage, ExceptionConstants.ERROR_TYPE_SYS);
    }

    public SysException(IBaseError baseError) {
        super(baseError.getErrorCode(), baseError.getErrorMessage(), ExceptionConstants.ERROR_TYPE_SYS);
    }
}

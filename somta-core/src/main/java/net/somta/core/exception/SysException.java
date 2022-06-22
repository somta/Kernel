package net.somta.core.exception;

public class SysException extends BaseException {

    public SysException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage, ExceptionConstants.ERROR_TYPE_SYS);
    }

}

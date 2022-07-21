package net.somta.core.exception;

public abstract class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    private long errorCode;

    /**
     * 错误消息
     */
    private String errorMessage;

    /**
     * 错误类型
     */
    private String errorType;

    /**
     * 异常信息
     */
    private Throwable throwable;

    protected BaseException(long errorCode, String errorMessage, String errorType) {
        super("BaseException(errorCode=" + errorCode + ",errorMessage=" + errorMessage + ",errorType=" + errorType + ")");
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }

    protected BaseException(long errorCode, String errorMessage, String errorType,Throwable throwable) {
        super("BaseException(errorCode=" + errorCode + ",errorMessage=" + errorMessage + ",errorType=" + errorType + ")",throwable);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
        this.throwable = throwable;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}

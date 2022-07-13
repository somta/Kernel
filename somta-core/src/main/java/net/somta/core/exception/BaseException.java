package net.somta.core.exception;

public abstract class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误消息
     */
    private String errorMessage;

    /**
     * 错误类型
     */
    private String errorType;

    protected BaseException(int errorCode, String errorMessage, String errorType) {
        super("BaseException(errorCode=" + errorCode + ",errorMessage=" + errorMessage + ",errorType=" + errorType + ")");
        this.errorMessage = errorMessage;
        this.errorType = errorType;

    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
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
}

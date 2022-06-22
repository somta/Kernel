package net.somta.core.exception;


import org.apache.commons.lang3.StringUtils;

public abstract class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    private String errorCode;

    /**
     * 错误消息
     */
    private String errorMessage;

    /**
     * 错误类型
     */
    private String errorType;

    protected BaseException(String errorCode, String errorMessage, String errorType) {
        super("BaseException(errorCode=" + errorCode + ",errorMessage=" + errorMessage + ",errorType=" + errorType + ")");
        if (StringUtils.isEmpty(errorCode)) {
            this.errorCode = ExceptionConstants.ERRORCODE_NOT_SUPPORT_ERROR;
        } else {
            this.errorCode = errorCode;
        }
        this.errorMessage = errorMessage;
        this.errorType = errorType;

    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
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

package com.matecoder.core.exception;

import com.matecoder.core.base.IBaseError;

/**
 * 基础异常类
 * @author husong
 */
public abstract class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    private long errorCode;

    /**
     * 错误消息
     */
    private String errorMsg;

    /**
     * 错误类型
     */
    private String errorType;

    /**
     * 异常信息
     */
    private Throwable throwable;

    protected BaseException(long errorCode, String errorMsg, String errorType) {
        super("errorCode:" + errorCode + ",errorMsg:" + errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorType = errorType;
        this.throwable = this;
    }

    protected BaseException(long errorCode, String errorMsg, String errorType,Throwable throwable) {
        super("errorCode:" + errorCode + ",errorMsg:" + errorMsg,throwable);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorType = errorType;
        this.throwable = throwable;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(long errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

    /**
     * 构建异常错误信息
     * @return 真实错误信息
     */
    protected static String buildExceptionErrorMsg(IBaseError baseError, Object... args){
        if(args != null && args.length > 0){
            return String.format(baseError.getErrorMsg(), args);
        }else{
            return baseError.getErrorMsg();
        }
    }
}

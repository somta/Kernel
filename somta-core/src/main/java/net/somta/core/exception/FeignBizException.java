package net.somta.core.exception;

import feign.FeignException;
import feign.Request;
import feign.Util;

/**
 * 自定义openfeign的异常
 * @author husong
 */
public class FeignBizException extends FeignException {

    private long errorCode;
    private String errorMsg;

    public FeignBizException(long errorCode, int status, String message, Request request) {
        super(status, (String) Util.checkNotNull(message, "message", new Object[0]), request);
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

    public FeignBizException(long errorCode, int status, String message, Request request, Throwable cause) {
        super(status, message, request, (Throwable)Util.checkNotNull(cause, "cause", new Object[0]));
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

    public long getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}

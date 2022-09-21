package net.somta.core.protocol;


import net.somta.core.base.IBaseError;

public class ResponseDataResult<T> extends ResponseResult {

    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static ResponseDataResult setResponseResult() {
    	return setResponseResult(null);
    }

    public static ResponseDataResult setResponseResult(Object data) {
        ResponseDataResult r = new ResponseDataResult();
        r.setSuccess(true);
        r.setResult(data);
        return r;
    }

    public static ResponseDataResult setErrorResponseResult(IBaseError baseError) {
        return setErrorResponseResult(baseError.getErrorCode(),baseError.getErrorMsg(), null);
    }

    public static ResponseDataResult setErrorResponseResult(long errorCode ,String errorMsg) {
    	return setErrorResponseResult(errorCode,errorMsg, null);
    }

    public static ResponseDataResult setErrorResponseResult(long errorCode ,String errorMsg,Object data) {
        ResponseDataResult r = new ResponseDataResult();
        r.setSuccess(false);
        r.setErrorCode(errorCode);
        r.setErrorMsg(errorMsg);
        r.setResult(data);
        return r;
    }

}

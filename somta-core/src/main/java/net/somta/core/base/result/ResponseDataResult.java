package net.somta.core.base.result;

import net.somta.core.exception.IBaseError;

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
        return setErrorResponseResult(baseError, null);
    }

    public static ResponseDataResult setErrorResponseResult(IBaseError baseError,Object data) {
        ResponseDataResult r = new ResponseDataResult();
        r.setSuccess(false);
        r.setErrorCode(baseError.getErrorCode());
        r.setErrorMessage(baseError.getErrorMessage());
        r.setResult(data);
        return r;
    }

}

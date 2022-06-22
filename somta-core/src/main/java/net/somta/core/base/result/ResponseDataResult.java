package net.somta.core.base.result;


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

    public static ResponseDataResult setErrorResponseResult(String errorCode ,String errorMessage) {
    	return setErrorResponseResult(errorCode,errorMessage, null);
    }

    public static ResponseDataResult setErrorResponseResult(String errorCode ,String errorMessage,Object data) {
        ResponseDataResult r = new ResponseDataResult();
        r.setSuccess(false);
        r.setErrorCode(errorCode);
        r.setErrorMessage(errorMessage);
        r.setResult(data);
        return r;
    }

}

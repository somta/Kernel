package net.somta.core.base.result;


import java.util.List;

public class ResponsePaginationDataResult<T> extends ResponseResult {

    private Long total;
    private List<T> result;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public static ResponsePaginationDataResult setPaginationDataResult(Long total, List resultList) {
        ResponsePaginationDataResult r = new ResponsePaginationDataResult();
        r.setTotal(total);
        r.setSuccess(true);
        r.setResult(resultList);
        return r;
    }

    public static ResponsePaginationDataResult setErrorResponseResult(String errorCode ,String errorMessage) {
    	return setErrorResponseResult(errorCode,errorMessage, null);
    }

    public static ResponsePaginationDataResult setErrorResponseResult(String errorCode ,String errorMessage,List data) {
        ResponsePaginationDataResult r = new ResponsePaginationDataResult();
        r.setTotal(0L);
        r.setSuccess(false);
        r.setErrorCode(errorCode);
        r.setErrorMessage(errorMessage);
        return r;
    }


}

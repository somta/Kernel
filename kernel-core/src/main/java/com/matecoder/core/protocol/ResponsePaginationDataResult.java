package com.matecoder.core.protocol;


import com.matecoder.core.base.IBaseError;

import java.util.List;

public class ResponsePaginationDataResult<T> extends ResponseResult {

    /**
     * 总条数
     */
    private Long total;
    /**
     * 结果集
     */
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

    public static ResponsePaginationDataResult setErrorResponseResult(IBaseError baseError) {
        return setErrorResponseResult(baseError, null);
    }

    public static ResponsePaginationDataResult setErrorResponseResult(IBaseError baseError, Object... args) {
        ResponsePaginationDataResult r = new ResponsePaginationDataResult();
        r.setTotal(0L);
        r.setSuccess(false);
        r.setErrorCode(baseError.getErrorCode());
        r.setErrorMsg(String.format(baseError.getErrorMsg(),args));
        return r;
    }


}

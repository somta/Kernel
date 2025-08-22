package com.matecoder.core.base;

/**
 * 业务应用统一实现该接口，来规范异常错误信息格式
 */
public interface IBaseError {

    /***
     * 错误码
     * @return 错误码
     */
    long getErrorCode();

    /**
     * 错误信息
     * @return 错误信息
     */
    String getErrorMsg();

}

package net.somta.core.base;

/**
 * 业务应用统一实现该接口，来规范异常错误信息格式
 * @author: husong
 **/
public interface IBaseError {

    long getErrorCode();

    String getErrorMsg();

}

package net.somta.core.base.result;

/**
 * 业务应用统一实现该接口，来规范错误信息格式
 */
public interface IBaseError {

    String getErrorCode();

    void setErrorCode(String errorCode);

    String getErrorMessage();

    void setErrorMessagge(String errorMessagge);
}

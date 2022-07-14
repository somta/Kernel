package net.somta.core.exception;

/**
 * @desc: 业务应用统一实现该接口，来规范异常错误信息格式
 * @author: husong
 * @date: 2022/7/8
 **/
public interface IBaseError {

    int getErrorCode();

    String getErrorMessage();

}

package com.matecoder.web.handlers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.StringJoiner;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger loger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${somta.application-id:}")
    private Integer applicationId;

    /**
     * 参数绑定错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseDataResult handleBindException(BindException ex) {
        StringJoiner sj = new StringJoiner(";");
        ex.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        return handleBizException(new BizException(WebErrorEnum.ARGUMENT_BIND_ERROR, sj.toString()));
    }

    /**
     * 参数校验错误
     *
     * @param ex
     * @return
     */
    /*@ExceptionHandler(ValidationException.class)
    public ResponseDataResult handleValidationException(ValidationException ex) {
        return handleBizException(new BizException("request.param.notvalid","参数绑定错误：" + ex.getMessage()));
        //return ResponseDataResult.setErrorResponseResult("request.param.notvalid","参数绑定错误：" + ex.getMessage());
    }*/

    /**
     * 字段校验不通过异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseDataResult handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        StringJoiner sj = new StringJoiner(";");
        ex.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        return handleBizException(new BizException(WebErrorEnum.ARGUMENT_VALID_ERROR, sj.toString()));
    }

    /**
     * Controller参数绑定错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseDataResult handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return handleSysException(new SysException(WebErrorEnum.REQUEST_ARGUMENT_ERROR,ex.getMessage()));
    }

    /**
     * 处理方法不支持异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseDataResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return handleSysException(new SysException(WebErrorEnum.REQUEST_METHOD_NOT_SUPPORT_ERROR));
    }

    /**
     * 处理自定义的openfeign异常
     * @param ex
     * @return
     */
    @ExceptionHandler(value = FeignBizException.class)
    public ResponseDataResult handleFeignBizException(FeignBizException ex) {
        loger.error(ex.getErrorCode() + ":" + ex.getMessage(), ex);
        return ResponseDataResult.setErrorResponseResult(ex.getErrorCode(),ex.getMessage());
    }

    /**
     * 处理自定义业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(BizException.class)
    public ResponseDataResult handleBizException(BizException ex) {
        long errorCode = ex.getErrorCode();
        if(applicationId != null){
            errorCode = (applicationId * 100 * 10000L) + ex.getErrorCode();
        }
        return ResponseDataResult.setErrorResponseResult(errorCode,ex.getErrorMsg());
    }

    /**
     * 处理自定义系统异常
     * @param ex
     * @return
     */
    @ExceptionHandler(SysException.class)
    public ResponseDataResult handleSysException(SysException ex) {
        if(ex.getThrowable() != null){
            loger.error(ex.getMessage(), ex);
        }
        return ResponseDataResult.setErrorResponseResult(ex.getErrorCode(),ex.getErrorMsg());
    }

    /**
     * 其他未知异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseDataResult handleException(Exception ex) {
        loger.error(ex.getMessage(), ex);
        long errorCode = 1;
        if(applicationId != null){
            errorCode = (applicationId * 1000000L) + 999999;
        }
        return ResponseDataResult.setErrorResponseResult(errorCode,"系统异常");
    }

}

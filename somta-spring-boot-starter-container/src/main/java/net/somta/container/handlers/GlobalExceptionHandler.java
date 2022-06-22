package net.somta.container.handlers;

import net.somta.core.base.result.ResponseDataResult;
import net.somta.core.exception.BaseException;
import net.somta.core.exception.BizException;
import net.somta.core.exception.SysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.StringJoiner;

/**
 * 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger loger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


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
        return handleBizException(new BizException("request.param.notvalid","参数绑定错误：" + sj.toString()));
        //return ResponseDataResult.setErrorResponseResult("request.param.notvalid","参数绑定错误：" + sj.toString());
    }

    /**
     * 参数校验错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseDataResult handleValidationException(ValidationException ex) {
        return handleBizException(new BizException("request.param.notvalid","参数绑定错误：" + ex.getMessage()));
        //return ResponseDataResult.setErrorResponseResult("request.param.notvalid","参数绑定错误：" + ex.getMessage());
    }

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
        return handleBizException(new BizException("request.param.notvalid","字段校验不通过异常：" + sj.toString()));
        //return ResponseDataResult.setErrorResponseResult("request.param.notvalid","字段校验不通过异常：" + sj.toString());
    }

    /**
     * Controller参数绑定错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseDataResult handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return handleSysException(new SysException("request.param.error","请求参数错误:"+ex.getMessage()));
        //return ResponseDataResult.setErrorResponseResult("request.param.error","请求参数错误:"+ex.getMessage());
    }

    /**
     * 处理方法不支持异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseDataResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return handleSysException(new SysException("request.method.not.support","请求方法不支持"));
        //return ResponseDataResult.setErrorResponseResult("request.method.not.support","请求方法不支持");
    }

    /**
     * 处理自定义业务异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BizException.class)
    public ResponseDataResult handleBizException(BizException ex) {
        loger.debug("进入BizException处理逻辑");
        return ResponseDataResult.setErrorResponseResult(ex.getErrorCode(),ex.getErrorMessage());
    }

    /**
     * 处理自定义系统异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(SysException.class)
    public ResponseDataResult handleSysException(SysException ex) {
        loger.debug("进入SysException处理逻辑");
        return ResponseDataResult.setErrorResponseResult(ex.getErrorCode(),ex.getErrorMessage());
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
        return ResponseDataResult.setErrorResponseResult("unknown.error","未知异常");
    }

}

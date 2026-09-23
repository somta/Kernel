package com.matecoder.web.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.somta.core.base.page.PageDataResult;
import net.somta.core.protocol.ResponseDataResult;
import net.somta.core.protocol.ResponsePaginationDataResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Type;

/**
 * 全局响应处理
 * @author husong
 * @date 2022/8/24
 **/
@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {

    private final static Logger logger = LoggerFactory.getLogger(GlobalResponseHandler.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        Type returnClassType = methodParameter.getParameterType();
        // 当返回类就是ResponseDataResult或ResponsePaginationDataResult不处理，直接返回
        if(returnClassType.equals(ResponseDataResult.class) ||
                returnClassType.equals(ResponsePaginationDataResult.class)){
            return false;
        }
        return true;
    }

    /**
     * 对返回数据进行处理
     * @param obj 返回对象
     * @param methodParameter 方法参数
     * @param mediaType 请求类型
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(isSkipHandler(serverHttpRequest)){
            return obj;
        }
        // 当返回类型是String时，用的是StringHttpMessageConverter转换器，无法转换为Json格式,需要序列化JSON后返回
        if(obj instanceof String){
            try {
                serverHttpResponse.getHeaders().set("Content-Type", "application/json;charset=utf-8");
                return objectMapper.writeValueAsString(ResponseDataResult.setResponseResult(obj));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        if(obj instanceof PageDataResult){
            PageDataResult pageDataResult = (PageDataResult) obj;
            return ResponsePaginationDataResult.setPaginationDataResult(pageDataResult.getTotal(),pageDataResult.getList());
        }
        //该方法返回的媒体类型是否是application/json,若不是，直接返回响应内容
        if (!mediaType.includes(MediaType.APPLICATION_JSON)) {
            return obj;
        }
        return ResponseDataResult.setResponseResult(obj);
    }

    /**
     * 是否跳过对响应的封装，如swagger的资源
     * @param serverHttpRequest
     * @return 是否跳过处理
     */
    private boolean isSkipHandler(ServerHttpRequest serverHttpRequest){
        String path = serverHttpRequest.getURI().getPath();
        //swagger相关接口
        boolean isSwaggerPath = path.startsWith("/v3/api-docs");
        if(StringUtils.isNotEmpty(path) && isSwaggerPath){
            return true;
        }
        //监控相关接口
        if( path.startsWith("/actuator")){
            return true;
        }
        return false;
    }
}

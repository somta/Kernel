package com.matecoder.xss.core;

import com.matecoder.xss.properties.XssProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;


/**
 * xss清理处理拦截器
 * @author husong
 * @date 2022/8/13
 **/
public class XssCleanInterceptor implements AsyncHandlerInterceptor {
	private final XssProperties xssProperties;

	public XssCleanInterceptor(XssProperties xssProperties) {
		this.xssProperties = xssProperties;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 非控制器请求直接跳出
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		// 是否开启xss的过滤
		if (!xssProperties.isEnabled()) {
			return true;
		}
		// 处理IgnoreXssClean注解,存在IgnoreXssClean注解就不拦截直接放行
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		IgnoreXssClean ignoreXssClean = handlerMethod.getMethodAnnotation(IgnoreXssClean.class);
		if (ignoreXssClean == null) {
			XssHolder.setEnable();
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		XssHolder.remove();
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		XssHolder.remove();
	}
}

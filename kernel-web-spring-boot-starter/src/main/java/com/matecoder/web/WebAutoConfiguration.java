package com.matecoder.web;


import com.matecoder.web.handlers.GlobalExceptionHandler;
import com.matecoder.web.handlers.GlobalResponseHandler;
import com.matecoder.web.runner.CheckApplicationRunner;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 自动装配配置类
 * @author husong
 **/
@Configuration
@ImportAutoConfiguration()
@Import({GlobalExceptionHandler.class, GlobalResponseHandler.class, CheckApplicationRunner.class})
public class WebAutoConfiguration {
}

package com.matecoder.xss;


import com.matecoder.xss.configuration.XssConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 自动装配配置类
 * @author husong
 **/
@Configuration
@ImportAutoConfiguration({XssConfiguration.class})
public class XssAutoConfiguration {
}

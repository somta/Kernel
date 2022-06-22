package net.somta.demo.configuration;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

/**
 * @Description //TODO
 * @Author husong
 * @Date 2021/4/2
 * @Version 1.0
 **/
public class LogConfiguration {

    @Bean
    public void builderLogger(){
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        // 将项目日志打印级别设置成debug级别，还可以根据包路径进行日志级别设置
        loggerContext.getLogger("root").setLevel(ch.qos.logback.classic.Level.toLevel("debug"));
    }

}

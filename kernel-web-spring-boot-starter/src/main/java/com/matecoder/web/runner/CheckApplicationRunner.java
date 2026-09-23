package com.matecoder.web.runner;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.TA_GridThemes;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.model.Dependency;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 核心的ApplicationRunner
 * @author husong
 * @date 2022/7/11
 **/
@Component
@Order(1)
public class CheckApplicationRunner implements ApplicationRunner {

    private final static Logger logger = LoggerFactory.getLogger(CheckApplicationRunner.class);

    private final ApplicationContext appContext;

    public CheckApplicationRunner(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(DevUtil.isInDevelopmentMode()){
            checkAllErrorEnums();
            checkMavenVersion();
        }
        printApplicationMessage();
    }

    /**
     * 打印应用信息
     * @throws UnknownHostException
     */
    private void printApplicationMessage() throws UnknownHostException {
        String ip = InetAddress.getLocalHost().getHostAddress();
        Environment environment = appContext.getEnvironment();
        String port = environment.getProperty("server.port","8080");
        String contextPath = environment.getProperty("server.servlet.context-path","/");
        if(!contextPath.endsWith("/")){
            contextPath = contextPath + "/";
        }
        AsciiTable at = new AsciiTable();
        at.addRule();
        at.addRow("API文档访问路径：http://"+ ip +":"+ port + contextPath +"doc.html");
        // todo 等这个方法移动到common后替换
        /*if(ProxyProperties.isDevelopmentMode()){
            at.addRule();
            at.addRow("当前为本地开发环境，请求会被代理，代理地址:" + ProxyProperties.getProxyUrl());
        }*/
        at.addRule();
        at.getContext().setWidth(100);
        at.getContext().setGridTheme(TA_GridThemes.OUTSIDE);
        System.out.println(at.render());
    }

    /**
     * 检查所有的异常枚举，防止编写重复code
     */
    private void checkAllErrorEnums(){
        // 存储所有的异常信息
        Map<Long,String> errorCodeMap = new HashMap<>(16);
        Reflections reflections = new Reflections("net.somta");
        Set<Class<? extends IBaseError>> errorClasses = reflections.getSubTypesOf(IBaseError.class);
        errorClasses.forEach(m->{
            try {
                IBaseError[] enumConstants = m.getEnumConstants();
                for (IBaseError errorEnum : enumConstants) {
                    if(errorCodeMap.containsKey(errorEnum.getErrorCode())){
                        logger.error("异常错误码:{}重复,异常信息:{}",errorEnum.getErrorCode(),errorEnum.getErrorMsg());
                        // 停止启动的SpringBoot
                        int exitCode = SpringApplication.exit(appContext,(ExitCodeGenerator) () -> 0);
                        System.exit(exitCode);
                    }
                    logger.debug("扫描到的枚举code:{},message:{}",errorEnum.getErrorCode(),errorEnum.getErrorMsg());
                    errorCodeMap.put(errorEnum.getErrorCode(),errorEnum.getClass().getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}

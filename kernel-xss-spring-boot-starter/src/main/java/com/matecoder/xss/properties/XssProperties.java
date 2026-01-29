package com.matecoder.xss.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * xss相关的配置类
 * @author husong
 **/
@ConfigurationProperties(prefix = XssProperties.PREFIX)
public class XssProperties {
    public static final String PREFIX = "kernel.xss";

    /**
     * 是否开启xss，默认关闭
     */
    private boolean enabled = true;
    /**
     * 模式：clear 清理，escape 转义（默认）
     */
    private XssMode mode = XssMode.escape;
    /**
     * 拦截的路由，默认为空
     */
    private List<String> pathPatterns = new ArrayList<>();
    /**
     * 放行的路由，默认为空
     */
    private List<String> pathExcludePatterns = new ArrayList<>();

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public XssMode getMode() {
        return mode;
    }

    public void setMode(XssMode mode) {
        this.mode = mode;
    }

    public List<String> getPathPatterns() {
        return pathPatterns;
    }

    public void setPathPatterns(List<String> pathPatterns) {
        this.pathPatterns = pathPatterns;
    }

    public List<String> getPathExcludePatterns() {
        return pathExcludePatterns;
    }

    public void setPathExcludePatterns(List<String> pathExcludePatterns) {
        this.pathExcludePatterns = pathExcludePatterns;
    }

    /**
     * xss的模式枚举
     */
    public enum XssMode {
        /**
         * 清理
         */
        clear,
        /**
         * 转义
         */
        escape;
    }
}

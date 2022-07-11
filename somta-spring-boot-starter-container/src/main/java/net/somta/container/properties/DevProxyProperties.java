package net.somta.container.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 本地开发代理相关配置
 */
@ConfigurationProperties(prefix = "somta.devproxy")
public class DevProxyProperties {
    private boolean enable = true;
    private String proxyUrl = "http://122.51.97.89:8888";

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }
}

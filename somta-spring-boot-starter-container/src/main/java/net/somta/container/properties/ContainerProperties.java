package net.somta.container.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 容器相关配置
 */
@ConfigurationProperties(prefix = "somta.container")
public class ContainerProperties {

    private Boolean enable = true;

    private String level = "WARN";

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}

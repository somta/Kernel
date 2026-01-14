package com.matecoder.core.context;

/**
 * 业务应用上下文，存放业务相关的信息
 * @author husong
 */
public class AppContext {
    /**
     * 灰度版本
     */
    private String grayVersion;

    protected AppContext() {
    }

    public String getGrayVersion() {
        return grayVersion;
    }

    public void setGrayVersion(String grayVersion) {
        this.grayVersion = grayVersion;
    }
}

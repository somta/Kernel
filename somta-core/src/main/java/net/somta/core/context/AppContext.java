package net.somta.core.context;

/**
 * 业务应用上下文，存放业务相关的信息
 * @author husong
 */
public class AppContext {
    private String grayVersion;

    protected AppContext(String grayVersion) {
        this.grayVersion = grayVersion;
    }

    public String getGrayVersion() {
        return grayVersion;
    }
}

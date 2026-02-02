package com.matecoder.core.enums;

/**
 * 传输枚举类
 * @author husong
 */
public enum TransportHttpHeaderKeyEnum {

    /**
     * 用户ID
     */
    USER_ID("M-User-Id"),
    /**
     * 租户ID
     */
    TENANT_ID("M-Tenant-Id"),
    /**
     * 灰度版本标识
     */
    GRAY_VERSION("M-Gray-Version"),
    /**
     * 远程应用名称
     */
    REMOTE_APPLICATION_NAME("M-Remote-Application-Name");

    /**
     * http请求头key
     */
    private final String httpHeaderKey;

    TransportHttpHeaderKeyEnum(String httpHeaderKey) {
        this.httpHeaderKey = httpHeaderKey;
    }

    public String getHttpHeaderKey() {
        return httpHeaderKey;
    }

}

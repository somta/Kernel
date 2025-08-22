package com.matecoder.core.enums;

/**
 * 传输枚举类
 * @author husong
 */
public enum TransportHttpHeaderKeyEnum {

    /**
     * 用户ID
     */
    USER_ID("S-User-Id"),
    /**
     * 租户ID
     */
    TENANT_ID("S-Tenant-Id"),
    /**
     * 灰度版本标识
     */
    GRAY_VERSION("S-Gray-Version"),
    /**
     * 远程应用名称
     */
    REMOTE_APPLICATION_NAME("S-Remote-Application-Name");

    /**
     * http请求头key
     */
    private String httpHeaderKey;

    TransportHttpHeaderKeyEnum(String httpHeaderKey) {
        this.httpHeaderKey = httpHeaderKey;
    }

    public String getHttpHeaderKey() {
        return httpHeaderKey;
    }

}

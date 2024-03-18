package net.somta.core.enums;

public enum TransportHttpHeaderKeyEnum {

    USER_ID("S-User-Id"),
    TENANT_ID("S-Tenant-Id"),
    GRAY_VERSION("S-Gray-Version");

    private String httpHeaderKey;

    TransportHttpHeaderKeyEnum(String httpHeaderKey) {
        this.httpHeaderKey = httpHeaderKey;
    }

    public String getHttpHeaderKey() {
        return httpHeaderKey;
    }

}

package com.matecoder.core.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 身份上下文，存放用户登录后相关的信息
 * @author husong
 */
public class IdentityContext {
    public static final String USER_ID = "userId";
    public static final String TENANT_ID = "tenantId";
    public static final String EXTEND = "extend";
    /**
     * 用户id
     */
    private final Long userId;

    /**
     * 租户id
     */
    private final Long tenantId;

    /**
     * 扩展map
     */
    private Map<String,String> extend = new HashMap<>();

    protected IdentityContext(Long userId, Long tenantId, Map<String, String> extend) {
        this.userId = userId;
        this.tenantId = tenantId;
        this.extend = extend;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public Map<String, String> getExtend() {
        return extend;
    }
}

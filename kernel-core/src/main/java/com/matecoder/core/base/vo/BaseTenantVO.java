package com.matecoder.core.base.vo;

/**
 * 基础租户VO
 * @author husong
 */
public class BaseTenantVO {

    /**
     * 租户ID
     */
    protected Long tenantId;

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}

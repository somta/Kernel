package com.matecoder.core.base.vo;

/**
 * 基础VO
 * @author husong
 */
public class BaseVO extends BaseTenantVO {

    /**
     * 主键ID
     */
    private Long id;

    public BaseVO(Long id) {
        this.id = id;
    }

    public BaseVO(Long id, Long tenantId) {
        this.id = id;
        super.tenantId = tenantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}

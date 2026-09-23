package com.matecoder.core.base.vo;

import com.matecoder.core.context.ApplicationContext;
import com.matecoder.core.context.IdentityContext;

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

    /**
     * 获取BaseVO实例
     * @param id 主键ID
     * @return BaseVO
     */
    public static BaseVO getInstance(Long id){
        BaseVO baseVo = new BaseVO(id);
        IdentityContext identityContext = ApplicationContext.getIdentityContext();
        if(identityContext != null){
            baseVo.setTenantId(identityContext.getTenantId());
        }
        return baseVo;
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

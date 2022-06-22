package net.somta.core.base;

import java.util.Date;

public class BaseModel {
    /**
     * 创建时间
     */
    private Date createdAt;
    /**
     * 创建人
     */
    private Integer createdBy;
    /**
     * 更新时间
     */
    private Date updatedAt;
    /**
     * 更新人
     */
    private Integer updatedBy;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }
}

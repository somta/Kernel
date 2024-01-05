package net.somta.core.base.page;

/**
 * 标准分页参数对象，跨领域层包装传递
 * @author: husong
 **/
public class PageParam {

    private Integer pageNum = 1;
    private Integer pageSize = 10;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getOffset() {
        return (pageNum-1) * pageSize;
    }

}

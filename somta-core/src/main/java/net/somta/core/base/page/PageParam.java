package net.somta.core.base.page;

/**
 * @desc: 标准分页参数对象，跨领域层包装传递
 * @author: husong
 * @date: 2022/7/28
 **/
public class PageParam {

    private int pageNum = 1;
    private int pageSize = 10;

    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}

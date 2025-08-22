package com.matecoder.core.base.page;

import java.util.List;

/**
 * 标准分页对象包装类，跨领域层包装传递
 * @author husong
 **/
public class PageDataResult<T extends List> {

    /**
     * 总页数
     **/
    private Long total;

    /**
     * 分页数据列表
     **/
    private T list;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public T getList() {
        return list;
    }

    public void setList(T list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageDataResult{" +
                "total=" + total +
                ", list=" + list +
                '}';
    }
}

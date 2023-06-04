package net.somta.core.base.page;

import java.util.List;

/**
 * 标准分页对象包装类，跨领域层包装传递
 * @author: husong
 **/
public class PageDataResult<T extends List> {

    /**
     * @desc: 总页数
     * @isNull: 不可为空
     **/
    private long total;

    /**
     * 分页数据列表
     * @isNull: 返回错误的时候可为空
     **/
    private T list;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
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

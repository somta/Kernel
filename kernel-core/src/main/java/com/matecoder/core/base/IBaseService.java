package com.matecoder.core.base;


import com.matecoder.core.protocol.ResponseDataResult;
import com.matecoder.core.protocol.ResponsePaginationDataResult;

import java.util.List;

/**
 * 基础service
 * @author husong
 */
public interface IBaseService<T> {

    abstract IBaseMapper getMapper();

    /**
     * 新增
     * @param t 实体参数
     * @return 返回结果
     */
    ResponseDataResult<Boolean> add(T t);

    /**
     * 根据ID删除
     * @param id 删除ID
     * @return 返回结果
     */
    ResponseDataResult<Boolean> deleteById(Object id);

    /**
     * 修改
     * @param t 入参
     * @return 返回结果
     */
    ResponseDataResult<Boolean> update(T t);

    /**
     * 根据Id查询
     * @param id 查询ID
     * @return 返回结果
     */
    T queryById(Object id);

    /**
     * 查询列表
     * @param param 查询参数
     * @return 返回结果列表
     */
    List<T> queryByList(Object param);

    /**
     * 查询列表总数
     * @param param 请求参数
     * @return 列表总数
     */
    Long queryListCount(Object param);

    /**
     * 查询分页列表
     * @param pageNum 页数
     * @param pageSize 每页条数
     * @param param 查询参数
     * @return 返回结果列表
     */
    ResponsePaginationDataResult<T> queryByPageList(Integer pageNum, Integer pageSize, Object param);

}

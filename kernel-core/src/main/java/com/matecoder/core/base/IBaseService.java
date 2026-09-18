package com.matecoder.core.base;


import com.github.pagehelper.PageInfo;
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
     * 根据ID物理删除
     * @param id 主键ID
     * @param tenantId 租户ID
     * @return 返回结果
     */
    ResponseDataResult<Boolean> logicalDeleteById(Long id,Long tenantId);

    /**
     * 根据ID物理删除
     * @param id 主键ID
     * @param tenantId 租户ID
     * @return 返回结果
     */
    ResponseDataResult<Boolean> deleteById(Long id,Long tenantId);

    /**
     * 修改
     * @param t 入参
     * @return 返回结果
     */
    ResponseDataResult<Boolean> update(T t);

    /**
     * 根据Id查询
     * @param id 主键ID
     * @param tenantId 租户ID
     * @return 返回结果
     */
    T queryById(Long id,Long tenantId);

    /**
     * 查询列表
     * @param param 查询参数
     * @return 返回结果列表
     */
    List<T> queryByList(Object param);

    /**
     * 查询分页列表
     * @param pageNum 页数
     * @param pageSize 每页条数
     * @param param 查询参数
     * @return 返回结果列表
     */
    PageInfo<T> queryByPageList(Integer pageNum, Integer pageSize, Object param);

}

package com.matecoder.core.base;

import com.matecoder.core.base.vo.BaseVO;

import java.util.List;

/**
 * 基础mapper
 * @author husong
 */
public interface IBaseMapper {
    /**
     * 新增
     * @param t 入参
     * @param <T> 实体
     * @return 返回 0，1
     */
    <T> int add(T t);

    /**
     * 根据ID物理删除
     * @param baseVo 基础vo
     * @return 返回 0，1
     */
    int deleteById(BaseVO baseVo);

    /**
     * 根据ID逻辑删除
     * @param baseVo 基础vo
     * @return 返回 0，1
     */
    int logicalDeleteById(BaseVO baseVo);

    /**
     * 更新
     * @param t 入参
     * @param <T> 实体
     * @return 返回 0，1
     */
    <T> int update(T t);

    /**
     * 根据ID查询
     * @param baseVo 查询ID
     * @param <T> 基础vo
     * @return 返回结果实体
     */
    <T> T queryById(BaseVO baseVo);

    /**
     * 查询列表总数
     * @param object 请求参数
     * @return 实体列表总数
     */
    Long queryListCount(Object object);

    /**
     * 查询列表
     * @param object 请求参数
     * @param <T> 实体
     * @return 实体列表
     */
    <T> List<T> queryByList(Object object);
}

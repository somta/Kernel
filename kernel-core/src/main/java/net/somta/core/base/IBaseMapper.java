package net.somta.core.base;

import java.util.List;

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
     * @param id 删除ID
     * @return 返回 0，1
     */
    int deleteById(Object id);

    /**
     * 更新
     * @param t 入参
     * @param <T> 实体
     * @return 返回 0，1
     */
    <T> int update(T t);

    /**
     * 根据ID查询
     * @param id 查询ID
     * @param <T> 实体
     * @return 返回结果实体
     */
    <T> T queryById(Object id);

    /**
     * 查询列表总数
     * @param object 请求参数
     * @return 实体列表总数
     */
    long queryListCount(Object object);

    /**
     * 查询列表
     * @param object 请求参数
     * @param <T> 实体
     * @return 实体列表
     */
    <T> List<T> queryByList(Object object);
}

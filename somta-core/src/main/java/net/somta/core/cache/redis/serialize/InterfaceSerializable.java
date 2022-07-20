package net.somta.core.cache.redis.serialize;

/**
 * @desc: 标准序列化接口
 * @author: husong
 * @date: 2022/7/12
 **/
public interface InterfaceSerializable {

    /**
     * 序列化
     *
     * @param value
     * @return
     */
    <T> String serialize(T value);

    /**
     * 反序列化
     *
     * @param data
     * @param clazz 反序列化的类型
     * @return
     */
    <T> T deserialize(String data, Class<T> clazz);

}

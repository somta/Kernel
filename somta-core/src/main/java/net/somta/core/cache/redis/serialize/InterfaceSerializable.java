package net.somta.core.cache.redis.serialize;

/**
 * 标准序列化接口
 * @author: husong
 **/
public interface InterfaceSerializable {

    /**
     * 序列化
     *
     * @param value serialize data
     * @return String
     */
    <T> String serialize(T value);

    /**
     * 反序列化
     *
     * @param data deserialize data
     * @param clazz 反序列化的类型
     * @return
     */
    <T> T deserialize(String data, Class<T> clazz);

    /**
     * 反序列化
     *
     * @param data deserialize data
     * @param clazz 反序列化的类型
     * @param elementClasses 元素的类型
     * @return
     */
    <T> T deserialize(String data, Class<T> clazz, Class<?> elementClasses);
}

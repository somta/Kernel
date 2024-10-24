package net.somta.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 统一序列化类
 * @author husong
 **/
public final class JsonSerializeHelper {

    public final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    /**
     * 序列化
     * @param value deserialize data
     * @return deserialize object instance
     */
    public static <T> String serialize(T value) throws JsonProcessingException {
       return objectMapper.writeValueAsString(value);
    }

    /**
     * 反序列化
     * @param value deserialize data
     * @param valueClass deserialize class
     * @return deserialize object instance
     */
    public static <T> T deserialize(String value, Class<T> valueClass) throws JsonProcessingException {
       return objectMapper.readValue(value, valueClass);
    }

    /**
     * 反序列化,支持反序列化带有泛型的类，并且序列化后的类携带泛型类，避免强转的问题
     * @param data deserialize data
     * @param clazz 集合类型
     * @param elementClass 元素类型
     * @return deserialize object instance
     */
    public static <T> T deserialize(String data, Class<T> clazz, Class<?> elementClass) throws JsonProcessingException {
      return objectMapper.readValue(data, getGenericsType(clazz, elementClass));
    }

    /**
     * 获取泛型类型
     * @param clazz 主类类型
     * @param elementClasses 主类下的泛型类类型
     * @return JavaType
     */
    private static JavaType getGenericsType(Class<?> clazz, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(clazz, elementClasses);
    }
}


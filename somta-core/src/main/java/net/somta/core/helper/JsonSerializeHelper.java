package net.somta.core.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.somta.core.enums.SystemErrorEnum;
import net.somta.core.exception.SysException;

import java.io.IOException;

/**
 * @desc: 统一序列化类
 * @author: husong
 * @date: 2022/7/12
 **/
public final class JsonSerializeHelper {

    public final static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }

    /**
     * 序列化
     * @param value
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> String serialize(T value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new SysException(SystemErrorEnum.SERIALIZE_ERROR,e);
        }
    }

    /**
     * 反序列化
     * @param value
     * @param valueClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T deserialize(String value, Class<T> valueClass)  {
        try {
            return objectMapper.readValue(value, valueClass);
        } catch (JsonProcessingException e) {
            throw new SysException(SystemErrorEnum.DSERIALIZE_ERROR,e);
        }
    }

    /**
     * 反序列化,支持反序列化带有泛型的类，并且序列化后的类携带泛型类，避免强转的问题
     * @param data
     * @param clazz 集合类型
     * @param elementClass 元素类型
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String data, Class<T> clazz, Class<?> elementClass) {
        try {
            return objectMapper.readValue(data, getGenericsType(clazz, elementClass));
        } catch (IOException e) {
            throw new SysException(SystemErrorEnum.DSERIALIZE_ERROR,e);
        }
    }

    /**
     * 获取泛型类型
     * @param clazz 主类类型
     * @param elementClasses 主类下的泛型类类型
     * @return
     */
    private static JavaType getGenericsType(Class<?> clazz, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(clazz, elementClasses);
    }
}


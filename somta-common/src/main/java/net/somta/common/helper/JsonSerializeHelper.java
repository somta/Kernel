package net.somta.common.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.somta.common.enums.CommonErrorEnum;
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
     *
     * @param value
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> String serialize(T value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new SysException(CommonErrorEnum.SERIALIZE_ERROR,e);
        }
    }

    /**
     * 反序列化
     *
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
            throw new SysException(CommonErrorEnum.DSERIALIZE_ERROR,e);
        }
    }

}


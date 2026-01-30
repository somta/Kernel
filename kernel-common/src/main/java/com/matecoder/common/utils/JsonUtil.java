package com.matecoder.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

/**
 * 统一序列化类
 * @author husong
 **/
public final class JsonUtil {

    public final static JsonMapper objectMapper = JsonMapper.builder()
            // 遇到JSON中未知的属性时不报错（兼容多余字段）
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            //属性名大小写不敏感匹配（如UserName <-> userName）
            .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
            // 空字符串转null
            .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
            .build();
    private static final TypeFactory TYPE_FACTORY = objectMapper.getTypeFactory();

    /**
     * 获取一个ObjectMapper实例
     * @return ObjectMapper Instance
     */
    public static ObjectMapper getObjectMapper(){
        return objectMapper;
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
    public static <T> T deserialize(String data, Class<?> clazz, Class<?> elementClass) throws JsonProcessingException {
        JavaType javaType = TYPE_FACTORY.constructParametricType(clazz, elementClass);
        return objectMapper.readValue(data, javaType);
    }

    /**
     * 反序列化，支持泛型类,泛型Map等
     * @param data JSON字符串
     * @param mainClass 主类类型
     * @param genericClasses 泛型类类型
     * @return 反序列化后的泛型对象
     */
    public static <T> T deserialize(String data, Class<?> mainClass, Class<?>... genericClasses) throws JsonProcessingException {
        JavaType javaType = TYPE_FACTORY.constructParametricType(mainClass, genericClasses);
        return objectMapper.readValue(data, javaType);
    }

    /**
     * 反序列化：支持嵌套泛型
     * @param data JSON字符串
     * @param javaType 完整的嵌套泛型类型（如ResponseDataResult<List<Student>>的JavaType）
     * @return 反序列化后的嵌套泛型对象
     */
    public static <T> T deserialize(String data, JavaType javaType) throws JsonProcessingException {
        return objectMapper.readValue(data, javaType);
    }

    /**
     * 获取泛型类型
     * @param clazz 主类类型
     * @param elementClasses 主类下的泛型类类型
     * @return JavaType
     */
    public static JavaType getGenericsType(Class<?> clazz, Class<?>... elementClasses) {
        return TYPE_FACTORY.constructParametricType(clazz, elementClasses);
    }

    /**
     * 获取嵌套泛型JavaType
     * 例如：构建 ResponseDataResult<List<Student>> 的JavaType
     * @param mainClass 最外层类（如ResponseDataResult.class）
     * @param nestedTypes 嵌套的泛型类型（如List<Student>的JavaType）
     * @return 完整的嵌套泛型JavaType
     */
    public static JavaType getNestedGenericsType(Class<?> mainClass, JavaType... nestedTypes) {
        return TYPE_FACTORY.constructParametricType(mainClass, nestedTypes);
    }
}


package com.matecoder.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Map相关操作工具类
 * @author husong
 **/
public class MapUtil {

    private static final Logger logger = LoggerFactory.getLogger(MapUtil.class);

    /**
     * map转java对象
     * @param map map
     * @param beanClass class
     * @return object instance
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) {
        if (map == null) {
            return null;
        }
        Object object = null;
        try {
            object = beanClass.newInstance();
            Field[] fields = beanClass.getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isFinal(mod) || Modifier.isStatic(mod)) {
                    continue;
                }
                field.setAccessible(true);
                field.set(object, map.get(field.getName()));
            }
        } catch (InstantiationException e) {
            logger.error("mapToObject instantiation failed for class: {}", beanClass.getName(), e);
        } catch (IllegalAccessException e) {
            logger.error("mapToObject access denied for class: {}", beanClass.getName(), e);
        }
        return object;
    }

    /**
     * java对象转map
     * @param obj object instance
     * @return map instance
     */
    public static Map<String, Object> objectToMap(Object obj){
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                logger.error("objectToMap access denied for field: {}", field.getName(), e);
            }
        }
        return map;
    }

    /**
     * 删除map中的空值,包括null和空字符串
     * @param map map
     */
    public static void removeNullValue(Map map){
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object obj = iterator.next();
            Object value = map.get(obj);
            if(value == null || (value instanceof String && StringUtils.isEmpty((String) value))){
                iterator.remove();
            }
        }
    }

}

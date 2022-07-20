package net.somta.core.cache.redis.serialize;

import net.somta.core.exception.SysException;
import net.somta.core.helper.JsonSerializeHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc: Json的序列化实现
 * @author: husong
 * @date: 2022/7/13
 **/
public class JsonSerializable implements InterfaceSerializable {

    private final static Logger logger = LoggerFactory.getLogger(JsonSerializable.class);

    @Override
    public <T> String serialize(T value) {
        try {
            return JsonSerializeHelper.serialize(value);
        } catch (SysException e) {
            logger.error("json serialize error", e);
            return null;
        }
    }

    @Override
    public <T> T deserialize(String data, Class<T> clazz) {
        try {
            return JsonSerializeHelper.deserialize(data, clazz);
        } catch (SysException e) {
            logger.error("json deserialize error", e);
            return null;
        }
    }
}

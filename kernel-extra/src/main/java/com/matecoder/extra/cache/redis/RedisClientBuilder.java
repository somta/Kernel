package com.matecoder.extra.cache.redis;

import com.matecoder.extra.cache.redis.client.AbstractRedisClient;
import com.matecoder.extra.cache.redis.client.ClusterRedisClient;
import com.matecoder.extra.cache.redis.client.SentinelRedisClient;
import com.matecoder.extra.cache.redis.client.SingleRedisClient;
import com.matecoder.extra.cache.redis.exception.RedisException;
import com.matecoder.extra.cache.redis.model.RedisConfigItem;
import com.matecoder.extra.cache.redis.model.RedisErrorEnum;
import com.matecoder.extra.cache.redis.model.RedisModeEnum;
import com.matecoder.extra.cache.redis.serialize.InterfaceSerializable;
import com.matecoder.extra.cache.redis.serialize.JsonSerializable;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Redis客户端构建类，构建不同类型的client
 * @author husong
 **/
public class RedisClientBuilder {

    private static final Map<String, AbstractRedisClient> redisClients = new HashMap<>();

    /**
     * 构建Redis客户端
     * @param redisConfigItem redis config
     * @return AbstractRedisClient
     */
    public static AbstractRedisClient buildRedisClient(RedisConfigItem redisConfigItem){
        return buildRedisClient(redisConfigItem,null);
    }

    /**
     * 构建Redis客户端
     * @param redisConfigItem redis config
     * @param interfaceSerializable an serializable
     * @return AbstractRedisClient
     */
    public synchronized static AbstractRedisClient buildRedisClient(RedisConfigItem redisConfigItem,
                                                       InterfaceSerializable interfaceSerializable){
        if(ArrayUtils.isEmpty(redisConfigItem.getAddress())){
            throw new RedisException(RedisErrorEnum.REDIS_ADDRESS_ERROR);
        }
        AbstractRedisClient cacheRedisClient = redisClients.get(buildClientCacheKey(redisConfigItem));
        if(cacheRedisClient != null){
            return cacheRedisClient;
        }

        if (interfaceSerializable == null) {
            interfaceSerializable = new JsonSerializable();
        }

        AbstractRedisClient redisClient = null;
        //根据类型实例化不同的类型
        if(RedisModeEnum.single.name().equals(redisConfigItem.getModel())){
            redisClient = new SingleRedisClient();
        }else if(RedisModeEnum.sentinel.name().equals(redisConfigItem.getModel())){
            redisClient = new SentinelRedisClient();
        }else if(RedisModeEnum.cluster.name().equals(redisConfigItem.getModel())){
            redisClient = new ClusterRedisClient();
        }else {
            throw new RedisException(RedisErrorEnum.REDIS_MODE_ERROR,redisConfigItem.getModel());
        }

        // init初始化配置
        redisClient.init(redisConfigItem);
        // 挂载序列化器
        redisClient.setInterfaceSerializable(interfaceSerializable);
        // 存储本地缓存
        redisClients.put(buildClientCacheKey(redisConfigItem),redisClient);
        return redisClient;
    }

    /**
     * 根据配置项生成缓存key
     * @param redisConfigItem redis配置
     * @return 缓存key
     */
    private static String buildClientCacheKey(RedisConfigItem redisConfigItem) {
        return Objects.hash(
                redisConfigItem.getModel(),
                Arrays.toString(redisConfigItem.getAddress())
        ) + "";
    }
}

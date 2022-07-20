package net.somta.core.cache.redis.client;


import net.somta.core.cache.redis.exception.RedisException;
import net.somta.core.cache.redis.model.RedisConfigItem;
import net.somta.core.cache.redis.model.RedisErrorEnum;
import net.somta.core.cache.redis.serialize.InterfaceSerializable;
import org.apache.commons.lang3.ArrayUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

/**
 * @desc: 抽象的Redis客户端
 * @author: husong
 * @date: 2022/7/12
 **/
public abstract class AbstractRedisClient {

    /**
     * redisson客户端
     */
    protected RedissonClient redissonClient;

    /**
     * 序列化器
     */
    private InterfaceSerializable interfaceSerializable;

    public void setInterfaceSerializable(InterfaceSerializable interfaceSerializable) {
        this.interfaceSerializable = interfaceSerializable;
    }

    /**
     * 初始化配置
     * @param redisConfigItem
     */
    public void init(RedisConfigItem redisConfigItem){
        if(ArrayUtils.isEmpty(redisConfigItem.getAddress())){
            throw new RedisException(RedisErrorEnum.REDIS_ADDRESS_ERROR);
        }
        Config redisConfig = new Config();
        // 默认编码器转换的字节流带有乱码，执行lua脚本或其它redis库读取就会报错
        redisConfig.setCodec(StringCodec.INSTANCE);

        // 初始化具体的客户端配置
        initClientConfig(redisConfig,redisConfigItem);

        // 创建Redisson Redis客户端
        this.redissonClient = Redisson.create(redisConfig);
    }

    /**
     * 获取redission的client
     * @return
     */
    public RedissonClient getRedissonClient(){
        return this.redissonClient;
    }

    /**
     * 获取序列化器
     * @return
     */
    public InterfaceSerializable getSerializable(){
        return this.interfaceSerializable;
    }

    /**
     * 初始化客户端配置,交给子类实现
     *
     * @param redisConfigItem
     */
    protected abstract void initClientConfig(Config redisConfig, RedisConfigItem redisConfigItem);


}

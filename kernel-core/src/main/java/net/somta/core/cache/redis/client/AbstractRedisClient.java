package net.somta.core.cache.redis.client;


import net.somta.core.cache.redis.model.RedisConfigItem;
import net.somta.core.cache.redis.serialize.InterfaceSerializable;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.redisson.config.Config;

/**
 * 抽象的Redis客户端
 * @author: husong
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
     * @param redisConfigItem redis config
     */
    public void init(RedisConfigItem redisConfigItem){
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
     * @return RedissonClient
     */
    public RedissonClient getRedissonClient(){
        return this.redissonClient;
    }

    /**
     * 获取序列化器
     * @return InterfaceSerializable
     */
    public InterfaceSerializable getSerializable(){
        return this.interfaceSerializable;
    }

    /**
     * 初始化客户端配置,交给子类实现
     * @param redisConfigItem redis配置
     */
    protected abstract void initClientConfig(Config redisConfig, RedisConfigItem redisConfigItem);

}

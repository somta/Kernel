package net.somta.core.cache.redis.client;

import net.somta.core.cache.redis.model.RedisConfigItem;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

/**
 * 单机的Redis客户端
 * @author: husong
 **/
public class SingleRedisClient extends AbstractRedisClient{
    @Override
    protected void initClientConfig(Config redisConfig, RedisConfigItem redisConfigItem) {
        String address = redisConfigItem.getAddress()[0].trim();
        int timeout = redisConfigItem.getTimeout();
        int retryAttempts = redisConfigItem.getRetryAttempts();
        int retryInterval = redisConfigItem.getRetryInterval();
        int poolSize = redisConfigItem.getPoolSize();
        int poolMinIdle = redisConfigItem.getPoolMinIdle();
        String password = redisConfigItem.getPassword();

        SingleServerConfig singleServerConfig = redisConfig.useSingleServer()
                .setAddress(address.startsWith(RedisConfigItem.PROTOCOL)
                        ? address
                        : RedisConfigItem.PROTOCOL + address)
                .setClientName(redisConfigItem.getClientName())
                .setTimeout(timeout)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setConnectionPoolSize(poolSize)
                .setConnectionMinimumIdleSize(poolMinIdle)
                .setDatabase(redisConfigItem.getDatabase());

        if (StringUtils.isNotBlank(password)) {
            singleServerConfig.setPassword(password);
        }
    }
}

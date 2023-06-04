package net.somta.core.cache.redis.client;

import net.somta.core.cache.redis.exception.RedisException;
import net.somta.core.cache.redis.model.RedisConfigItem;
import net.somta.core.cache.redis.model.RedisErrorEnum;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.redisson.config.ReadMode;
import org.redisson.config.SentinelServersConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 哨兵的Redis客户端
 * @author: husong
 **/
public class SentinelRedisClient extends AbstractRedisClient {
    @Override
    protected void initClientConfig(Config redisConfig, RedisConfigItem redisConfigItem) {
        String sentinelMaster = redisConfigItem.getSentinelMaster();
        if(StringUtils.isEmpty(sentinelMaster)){
            throw new RedisException(RedisErrorEnum.REDIS_SENTINEL_MASTER_NAME_ERROR);
        }
        String[] sentinelNodes = redisConfigItem.getAddress();
        int timeout = redisConfigItem.getTimeout();
        int poolSize = redisConfigItem.getPoolSize();
        String password = redisConfigItem.getPassword();

        List<String> sentinelNodeList = new ArrayList<>(sentinelNodes.length);
        Arrays.stream(sentinelNodes).forEach(address -> sentinelNodeList.add(
                address.startsWith(RedisConfigItem.PROTOCOL) ? address.trim()
                        : RedisConfigItem.PROTOCOL + address.trim()));

        SentinelServersConfig sentinelServersConfig = redisConfig.useSentinelServers()
                .addSentinelAddress(sentinelNodeList.toArray(new String[0]))
                .setClientName(redisConfigItem.getClientName())
                .setMasterName(sentinelMaster)
                .setReadMode(ReadMode.SLAVE)
                .setTimeout(timeout)
                .setMasterConnectionPoolSize(poolSize)
                .setSlaveConnectionPoolSize(poolSize)
                .setDatabase(redisConfigItem.getDatabase());

        if (StringUtils.isNotBlank(password)) {
            sentinelServersConfig.setPassword(password);
        }
    }
}

package net.somta.core.cache.redis.client;

import net.somta.core.cache.redis.model.RedisConfigItem;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @desc: 集群的Redis客户端
 * @author: husong
 * @date: 2022/7/12
 **/
public class ClusterRedisClient extends AbstractRedisClient {
    @Override
    protected void initClientConfig(Config redisConfig, RedisConfigItem redisConfigItem) {
        String[] clusterNodes = redisConfigItem.getAddress();
        int timeout = redisConfigItem.getTimeout();
        int retryAttempts = redisConfigItem.getRetryAttempts();
        int retryInterval = redisConfigItem.getRetryInterval();
        int scanInterval = redisConfigItem.getScanInterval();
        int connectTimeout = redisConfigItem.getConnectTimeout();
        int masterConnectionPoolSize = redisConfigItem.getMasterConnectionPoolSize();
        int slaveConnectionPoolSize = redisConfigItem.getSlaveConnectionPoolSize();
        String password = redisConfigItem.getPassword();

        List<String> clusterNodeList = new ArrayList<>(clusterNodes.length);
        Arrays.stream(clusterNodes).forEach(address -> clusterNodeList.add(
                address.startsWith(RedisConfigItem.PROTOCOL) ? address.trim()
                        : RedisConfigItem.PROTOCOL + address.trim()));

        ClusterServersConfig clusterServersConfig = redisConfig.useClusterServers()
                .addNodeAddress(clusterNodeList.toArray(new String[0]))
                .setClientName(redisConfigItem.getClientName())
                .setScanInterval(scanInterval)
                .setConnectTimeout(connectTimeout)
                .setRetryAttempts(retryAttempts)
                .setRetryInterval(retryInterval)
                .setMasterConnectionPoolSize(masterConnectionPoolSize)
                .setSlaveConnectionPoolSize(slaveConnectionPoolSize)
                .setTimeout(timeout);

        if (StringUtils.isNotBlank(password)) {
            clusterServersConfig.setPassword(password);
        }
    }
}

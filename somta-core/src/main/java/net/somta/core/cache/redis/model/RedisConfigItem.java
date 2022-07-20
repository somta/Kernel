package net.somta.core.cache.redis.model;

/**
 * @desc: Redis配置类
 * @author: husong
 * @date: 2022/7/12
 **/
public class RedisConfigItem {

    // redis协议
    public static final String PROTOCOL = "redis://";

    /**
     * redis的模式 单机模式：single  哨兵模式：sentinel 集群模式：cluster
     */
    private String model = "single";
    /**
     * Redis地址
     */
    private String[] address;
    /**
     * Redis密码
     */
    private String password;
    /**
     * 客户端名称
     */
    private String clientName;
    /**
     * Redis数据库索引（默认为0）
     */
    private Integer database = 0;
    /**
     * 连接超时时间（毫秒）
     */
    private Integer timeout = 5000;
    /**
     * 命令失败重试次数
     */
    private Integer retryAttempts = 3;
    /**
     * 命令重试发送时间间隔
     */
    private Integer retryInterval = 1500;
    /**
     * 连接池大小
     */
    private Integer poolSize = 64;
    /**
     * 连接池中的最小空闲连接
     */
    private Integer poolMinIdle = 64;
    /**
     * 连接超时时间
     */
    private Integer poolConnectTimeout = 10000;


    ///////////////////哨兵配置//////////////////////
    /**
     * 哨兵模式监测的主服务器名称
     */
    private String sentinelMaster;


    ///////////////////集群配置//////////////////////
    /**
     * 集群状态扫描间隔时间，单位是毫秒
     */
    private Integer scanInterval = 5000;
    // 主节点连接池大小
    private Integer masterConnectionPoolSize = 64;
    // 从节点连接池大小
    private Integer slaveConnectionPoolSize = 64;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String[] getAddress() {
        return address;
    }

    public void setAddress(String[] address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getRetryAttempts() {
        return retryAttempts;
    }

    public void setRetryAttempts(Integer retryAttempts) {
        this.retryAttempts = retryAttempts;
    }

    public Integer getRetryInterval() {
        return retryInterval;
    }

    public void setRetryInterval(Integer retryInterval) {
        this.retryInterval = retryInterval;
    }

    public Integer getPoolSize() {
        return poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public Integer getPoolMinIdle() {
        return poolMinIdle;
    }

    public void setPoolMinIdle(Integer poolMinIdle) {
        this.poolMinIdle = poolMinIdle;
    }

    public Integer getPoolConnectTimeout() {
        return poolConnectTimeout;
    }

    public void setPoolConnectTimeout(Integer poolConnectTimeout) {
        this.poolConnectTimeout = poolConnectTimeout;
    }

    public String getSentinelMaster() {
        return sentinelMaster;
    }

    public void setSentinelMaster(String sentinelMaster) {
        this.sentinelMaster = sentinelMaster;
    }

    public Integer getScanInterval() {
        return scanInterval;
    }

    public void setScanInterval(Integer scanInterval) {
        this.scanInterval = scanInterval;
    }

    public Integer getMasterConnectionPoolSize() {
        return masterConnectionPoolSize;
    }

    public void setMasterConnectionPoolSize(Integer masterConnectionPoolSize) {
        this.masterConnectionPoolSize = masterConnectionPoolSize;
    }

    public Integer getSlaveConnectionPoolSize() {
        return slaveConnectionPoolSize;
    }

    public void setSlaveConnectionPoolSize(Integer slaveConnectionPoolSize) {
        this.slaveConnectionPoolSize = slaveConnectionPoolSize;
    }
}

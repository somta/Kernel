package net.somta.core.cache.redis.model;

public enum RedisModeEnum {

    /**
     * 单机模式
     */
    single,

    /**
     * 哨兵模式
     */
    sentinel,

    /**
     * 集群模式
     */
    cluster;
}

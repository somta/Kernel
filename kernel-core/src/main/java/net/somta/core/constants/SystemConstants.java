package net.somta.core.constants;

public class SystemConstants {
    /**
     * 框架底层统一前缀
     */
    public final static int BASE_CODE_PREFIX = 100;

    /**
     * core的code
     */
    public final static long CORE_CODE = (BASE_CODE_PREFIX * 1000 + 001) * 10000;

    /**
     * common的code
     */
    public final static long COMMON_CODE = (BASE_CODE_PREFIX * 1000 + 002) * 10000;

    /**
     * 缓存starter的code
     */
    public final static long STATER_CACHE_CODE = (BASE_CODE_PREFIX * 1000 + 100) * 10000;

    /**
     * license starter的code
     */
    public final static long STATER_LICENSE_CODE = (BASE_CODE_PREFIX * 1000 + 200) * 10000;

    /**
     * 分布式锁starter的code
     */
    public final static long STATER_LOCK_CODE = (BASE_CODE_PREFIX * 1000 + 300) * 10000;

    /**
     * 微服务starter的code
     */
    public final static long STATER_MICRO_CODE = (BASE_CODE_PREFIX * 1000 + 400) * 10000;

    /**
     * mq starter的code
     */
    public final static long STATER_MQ_CODE = (BASE_CODE_PREFIX * 1000 + 500) * 10000;

    /**
     * orm starter的code
     */
    public final static long STATER_ORM_CODE = (BASE_CODE_PREFIX * 1000 + 600) * 10000;

    /**
     * ElasticSearch starter的code
     */
    public final static long STATER_SEARCH_CODE = (BASE_CODE_PREFIX * 1000 + 700) * 10000;

    /**
     * Storage starter的code
     */
    public final static long STATER_STORAGE_CODE = (BASE_CODE_PREFIX * 1000 + 800) * 10000;

    /**
     * Web starter的code
     */
    public final static long STATER_WEB_CODE = (BASE_CODE_PREFIX * 1000 + 900) * 10000;
}

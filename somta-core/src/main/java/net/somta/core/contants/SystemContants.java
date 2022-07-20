package net.somta.core.contants;

/**
 * 系统统一常量
 */
public class SystemContants {

    /**
     * 框架底层统一前缀
     */
    public final static int BASE_CODE_PREFIX = 100;

    /**
     * common的code
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
     * 分布式锁starter的code
     */
    public final static long STATER_LOCK_CODE = (BASE_CODE_PREFIX * 1000 + 200) * 10000;
}

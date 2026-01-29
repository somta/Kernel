package com.matecoder.common.utils;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 雪花id工具类，总共64位
 * 第一位保留位 + 41位时间戳 + 随机12位 + sequence10位
 * 使用随机12位代替机器ID，防止同机器下多pod会重复的问题
 * @author gavin
 */
public class SnowflakeIdUtil {
    // 日志实例
    private static final Logger logger = LoggerFactory.getLogger(SnowflakeIdUtil.class);

    // 单例实例（懒汉式，线程安全）
    private static volatile SnowflakeIdUtil INSTANCE;

    /** 起始时间戳 (2022-06-22 00:00:00) */
    private final long startTime = 1655827200000L;

    /** 随机id所占的位数 */
    private final long randomIdBits = 12L;

    /** 支持的最大随机id (0-4095) */
    private final long maxRandomId = -1 ^ (-1 << randomIdBits);

    /** 序列在id占的位数 */
    private final long sequenceBits = 10L;

    /** 随机ID向左移10位 */
    private final long randomIdShift = sequenceBits;

    /** 时间戳向左移22位 (10+12) */
    private final long timestampLeftShift = sequenceBits + randomIdBits;

    /** 生成序列的掩码 (10位，最大值1023) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 随机ID (私有化，防止外部修改) */
    private final long randomId;

    /** 毫秒内序列(0~1023) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    /**
     * 私有构造方法，防止外部实例化
     */
    private SnowflakeIdUtil() {
        // 修正随机数范围：[0, 4095]，符合12位的最大值
        this.randomId =RandomUtils.secure().randomLong(0, maxRandomId + 1);
        logger.info("雪花ID工具类初始化，随机ID: {}", randomId);
    }

    /**
     * 获取单例实例（双重检查锁，线程安全）
     */
    private static SnowflakeIdUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (SnowflakeIdUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SnowflakeIdUtil();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 对外提供的静态方法：获取雪花ID
     * @return 雪花ID
     */
    public static long generateSnowflakeId() {
        return getInstance().nextId();
    }

    /**
     * 生成雪花ID（核心方法，同步控制）
     * @return SnowflakeId
     */
    private synchronized long nextId() {
        long currentTimestamp = getCurrentTimestamp();

        // 处理时钟回拨问题（更友好的异常信息）
        if (currentTimestamp < lastTimestamp) {
            long timeDiff = lastTimestamp - currentTimestamp;
            logger.error("时钟回拨检测：当前时间戳({}) 小于上次生成ID的时间戳({})，差值：{}ms",currentTimestamp, lastTimestamp, timeDiff);
            throw new RuntimeException(String.format("时钟回拨异常，禁止生成ID！当前时间戳：%d，上次时间戳：%d，差值：%dms",currentTimestamp, lastTimestamp, timeDiff));
        }

        // 同一毫秒内，序列自增
        if (lastTimestamp == currentTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 序列溢出，等待下一个毫秒
            if (sequence == 0) {
                currentTimestamp = tilNextMillis(lastTimestamp);
                logger.debug("同一毫秒内序列耗尽，等待下一个毫秒，新时间戳：{}", currentTimestamp);
            }
        } else {
            // 不同毫秒，重置序列
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;

        // 组合最终ID
        return ((currentTimestamp - startTime) << timestampLeftShift)
                | (randomId << randomIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimestamp();
            // 增加少量休眠，减少CPU空转
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warn("等待下一个毫秒时线程被中断", e);
                throw new RuntimeException("生成ID时线程中断", e);
            }
        }
        return timestamp;
    }

    /**
     * 获取当前时间（毫秒）
     * 私有化，便于后续扩展（如替换为System.nanoTime()）
     * @return 当前时间(毫秒)
     */
    private long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
}
package net.somta.common.utils;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 雪花id工具类，总共64位 第一位保留位 + 41位时间戳 + 随机12位 + sequence10位,
 * 使用随机12位代替机器ID，防止同机器下多pod会重复的问题，同时将序列号减少了2位，加到机器ID上了
 * @author: husong
 **/
public class SnowflakeIdUtil {
	private Logger logger = LoggerFactory.getLogger(SnowflakeIdUtil.class);
    /**
     * 起始时间
     */
    private final long startTime = 1655827200000L;

    /** 随机id所占的位数 */
    private final long randomIdBits = 12L;

    /** 支持的最大随机id  0-31*/
    private final long maxRandomId = -1 ^ (-1 << randomIdBits);

    /** 序列在id占的位数 */
    private final long sequenceBits = 10L;

    /** 随机ID向左移10位 */
    private final long randomIdShift = sequenceBits;

    /** 时间戳向左移22位 */
    private final long timestampLeftShift = sequenceBits + randomIdBits;

    /** 生成序列的掩码 */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 随机ID */
    private long randomId;

    /** 毫秒内序列(0~1024) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    public SnowflakeIdUtil() {
        randomId = RandomUtils.nextInt(1,1 << 12);
    }

    /**
     * 获雪花ID
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long currentTimestamp = getCurrentTimestamp();
        if (currentTimestamp < lastTimestamp) {
            throw new RuntimeException("时钟混乱了");
        }

        if (lastTimestamp == currentTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                currentTimestamp = tilNextMillis(lastTimestamp);
            }
        }else {
            sequence = 0L;
        }

        lastTimestamp = currentTimestamp;
        return ((currentTimestamp - startTime) << timestampLeftShift)
                | (randomId << randomIdShift)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimestamp();
        }
        return timestamp;
    }

    /**
     * 获取当前时间（毫秒）
     * @return 当前时间(毫秒)
     */
    protected long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

}
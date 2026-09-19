package com.matecoder.common.utils;

/**
 * 环境判断工具类
 * @author husong
 */
public class DevUtil {

    private DevUtil() {
    }

    /**
     * 是否是开发模式
     * @return boolean
     */
    public static boolean isInDevelopmentMode() {
        return isWindows()
                || isMac()
                || "true".equalsIgnoreCase(System.getenv("ENABLE_LINUX_DEVELOP"));
    }

    /**
     *  是否是Window系统
     * @return boolean
     */
    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        if (os != null && (os.startsWith("win") || os.startsWith("Win"))) {
            return true;
        }
        return false;
    }

    /**
     * 是否是Mac系统
     * @return boolean
     */
    public static boolean isMac() {
        String os = System.getProperty("os.name");
        if (os != null && (os.startsWith("Mac") || os.startsWith("mac"))) {
            return true;
        }
        return false;
    }

    /**
     * 获取机器CPU位数
     * @return CPU位数（32或64）
     */
    public static int getMachineCpuBit() {
        return Integer.valueOf(System.getProperty("sun.arch.data.model"));
    }

}

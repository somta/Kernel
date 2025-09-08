package com.matecoder.common.encrypt;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 摘要工具类
 * @since 3.0.0
 */
public class DigestUtil {

    /**
     * 十六进制字符数组，用于快速转换。
     * '0' 到 '9' 对应 0-9, 'a' 到 'f' 对应 10-15。
     */
    private static final char[] HEX_CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    public static String sha1Hex(String data) {
        if (data == null) {
            return null;
        }
        return sha1Hex(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 计算字节数组的 SHA-1 哈希值，并返回其十六进制字符串表示。
     *
     * @param data 要计算哈希的字节数组，可以为 null
     * @return SHA-1 哈希值的十六进制字符串，如果输入为 null 则返回 null
     */
    public static String sha1Hex(byte[] data) {
        if (data == null) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] hashBytes = digest.digest(data);
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-1 algorithm not available", e);
        }
    }

    /**
     * 将字节数组转换为十六进制字符串。
     * @param bytes 要转换的字节数组
     * @return 对应的十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        StringBuilder hexString = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            // 使用无符号右移和位与操作获取高4位和低4位
            hexString.append(HEX_CHARS[(b >> 4) & 0x0F]);
            hexString.append(HEX_CHARS[b & 0x0F]);
        }
        return hexString.toString();
    }
}

package net.somta.common.encrypt;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Base64工具类
 * @author husong
 **/
public final class Base64Util {

    /**
     * 将原字符串进行base64
     * @param srcStr: 原文字符串
     * @return base64之后的字符串
     */
    public static String encode(String srcStr) {
        if(srcStr == null){
            return null;
        }
        return Base64.getEncoder().encodeToString(srcStr.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将字节数组进行base64
     * @param srcBytes 原始字节数组
     * @return 编码后的字符串
     */
    public static String encode(byte[] srcBytes) {
        if(srcBytes == null || srcBytes.length == 0){
            return null;
        }
        return Base64.getEncoder().encodeToString(srcBytes);
    }

    /**
     * 将base64字符串还原成原来的字符串
     * @param base64Str: base64字符串
     * @return 原始字符串
     */
    public static String decode(String base64Str) {
        if(base64Str == null){
            return null;
        }
        byte[] decodeStr = Base64.getDecoder().decode(base64Str.getBytes(StandardCharsets.UTF_8));
        return new String(decodeStr,StandardCharsets.UTF_8);
    }

    /**
     * 将base64字符串还原成原始字节数组
     * @param base64Str: base64字符串
     * @return 原始字节数组
     */
    public static byte[] decodeToByte(String base64Str) {
        if(base64Str == null){
            return null;
        }
        return Base64.getDecoder().decode(base64Str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将原始url字符串进行base64
     * @param srcUrl: 原url字符串
     * @return base64后的url
     */
    public static String encodeUrl(String srcUrl) {
        if(srcUrl == null){
            return null;
        }
        return Base64.getUrlEncoder().encodeToString(srcUrl.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 将base64的url字符串还原成原始的url
     * @param base64Url: base64的url字符串
     * @return 原始的url
     */
    public static String decodeUrl(String base64Url) {
        if(base64Url == null){
            return null;
        }
        byte[] decode = Base64.getUrlDecoder().decode(base64Url.getBytes(StandardCharsets.UTF_8));
        return new String(decode,StandardCharsets.UTF_8);
    }

}

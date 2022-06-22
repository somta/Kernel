package net.somta.common.utils.encrypt;

/**
 * 加密解密公共接口
 */
public interface Coder {

    /**
     * 加密
     * @param var1 加密字符串
     * @return String
     */
    String encode(byte[] var1);

    /**
     * 解密
     * @param var1 解密字符串
     * @return String
     */
    byte[] decode(String var1);
}

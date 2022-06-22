package net.somta.common.utils.encrypt;

public final class Base64 {

    private static final Base64Coder coder = new Base64Coder();

    public Base64() {
    }

    /**
     * 字符串进行BASE加密
     * @param enCode 加密字符串
     * @return String
     */
    public static String encode(String enCode) {
        byte[] enCodeBytes = enCode.getBytes();
        return coder.encode(enCodeBytes);
    }

    /**
     * 字符串进行BASE解密
     * @param deCode 解密字符串
     * @return String
     */
    public static String decode(String deCode) {
        return new String(coder.decode(deCode));
    }

    /**
     * 字节数组进行BASE64加密
     * @param enCodeBytes 加密字节数组
     * @return String
     */
    public static String encodeByte(byte[] enCodeBytes) {
        return coder.encode(enCodeBytes);
    }

    /**
     * 字节数组进行BASE64解密
     * @param enCodeBytes 解密字节数组
     * @return String
     */
    public static String decodeByte(byte[] enCodeBytes) {
        return new String(enCodeBytes);
    }
}

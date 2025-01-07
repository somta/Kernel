package net.somta.common.encrypt;

/**
 * 对称加密 填充算法 枚举
 * 
 * @author husong
 */
public enum PaddingEnum {
    /**
     * PKCS5算法
     */
    PKCS5_PADDING("PKCS5Padding"),
    /**
     * PKCS7算法
     */
    PKCS7_PADDING("PKCS7Padding");

    private final String name;

    PaddingEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

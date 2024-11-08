package net.somta.common.encrypt;

/**
 * 对称加密 填充算法枚举
 * 
 * @author husong
 */
public enum PaddingEnum {
    /**
     * PKCS5算法
     */
    PKCS5PADDING("PKCS5Padding"),
    /**
     * PKCS7算法
     */
    PKCS7PADDING("PKCS7Padding"),
    /**
     * 无填充，特别说明无填充模式要求原文长度必须是8byte的整数倍
     */
    NOPADDING("NoPadding");

    private final String name;

    PaddingEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

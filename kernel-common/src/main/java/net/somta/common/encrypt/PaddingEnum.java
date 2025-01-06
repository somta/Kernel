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
    PKCS7_PADDING("PKCS7Padding"),
    /**
     * 无填充，特别说明无填充模式要求原文长度必须是8byte的整数倍
     */
    NO_PADDING("NoPadding");

    private final String name;

    PaddingEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PaddingEnum getEnumByName(String name) {
        for (PaddingEnum padding : values()) {
            if (padding.getName().equalsIgnoreCase(name)) {
                return padding;
            }
        }
        throw new IllegalArgumentException("No enum constant with name: " + name);
    }
}

package net.somta.common.encrypt;

/**
 * 对称加密 密码模式 枚举
 * @author husong
 */
public enum KeyModeEnum {
    /**
     * 电子密码本(Electronic codebook)
     */
    ECB("ECB"),
    /**
     * 密码块链接(Cipher-block chaining)
     */
    CBC("CBC");

    private final String name;

    KeyModeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static KeyModeEnum getEnumByName(String name) {
        for (KeyModeEnum keyMode : values()) {
            if (keyMode.getName().equalsIgnoreCase(name)) {
                return keyMode;
            }
        }
        throw new IllegalArgumentException("No enum constant with name: " + name);
    }
}

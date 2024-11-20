package net.somta.common.encrypt;

/**
 * 密钥对
 * 
 * @author husong
 */
public class KeyPairString {
    /**
     * 私钥
     */
    private String privateKey;
    /**
     * 公钥
     */
    private String publicKey;

    public KeyPairString() {

    }

    public KeyPairString(String privateKey, String publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "KeyPairString{" +
                "privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                '}';
    }
}

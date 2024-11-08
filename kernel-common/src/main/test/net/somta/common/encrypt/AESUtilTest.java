package net.somta.common.encrypt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESUtilTest {

    /**
     * 密钥字符串 32位
     */
    private final String secretKeyStr = "123456789qwertyuiopasdfghjklzxcv";

    /**
     * 默认原始数据
     */
    private final String srcContent = "hello world";

    /**
     * 加密后base64加密数据
     */
    private final String aesBase64Content = "lO3nMg9sFtnWo8r0zmWyfw==";

    @Test
    public void generateSecretKeyTest(){
        SecretKey key = AESUtil.generateSecretKey(128);
        System.out.println(key);
    }

    @Test
    public void stringToSecretKeyTest() throws Exception {
        SecretKeySpec secretKey = AESUtil.stringToSecretKey(secretKeyStr);
        System.out.println(secretKey);
    }

    @Test
    public void encryptTest() throws Exception {
        SecretKey secretKey = AESUtil.stringToSecretKey(secretKeyStr);

        String tempAesBase64Content =AESUtil.encrypt(srcContent,secretKey);
        System.out.println(tempAesBase64Content);
        Assertions.assertEquals(aesBase64Content,tempAesBase64Content);
    }

    @Test
    public void decryptTest() throws Exception {
        SecretKey secretKey = AESUtil.stringToSecretKey(secretKeyStr);

        String tempSrcContent = AESUtil.decrypt(aesBase64Content,secretKey);
        Assertions.assertEquals(srcContent,tempSrcContent);
    }

    @Test
    public void encryptAndDecryptByGenerateTest() throws Exception {
        //随机生成一个密钥
        SecretKey secretKey = AESUtil.generateSecretKey(128);

        String tempAesBase64Content =AESUtil.encrypt(srcContent,secretKey);
        String tempSrcContent = AESUtil.decrypt(tempAesBase64Content,secretKey);
        Assertions.assertEquals(srcContent,tempSrcContent);
    }

    @Test
    public void encryptAndDecryptByCbcTest() throws Exception {
        //随机生成一个密钥
        SecretKey secretKey = AESUtil.generateSecretKey(128);

        String tempAesBase64Content =AESUtil.encrypt(srcContent,secretKey,KeyModeEnum.CBC);
        String tempSrcContent = AESUtil.decrypt(tempAesBase64Content,secretKey,KeyModeEnum.CBC);
        Assertions.assertEquals(srcContent,tempSrcContent);
    }

}

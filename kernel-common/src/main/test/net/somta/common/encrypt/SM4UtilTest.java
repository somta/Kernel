package net.somta.common.encrypt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SM4UtilTest {

    /**
     * 密钥
     */
    private final String secretKey = "GpIKjKqpJcJ2SIP/iHcWyQ==";

    /**
     * 默认原始数据
     */
    private final String srcContent = "hello kernel";

    @Test
    public void generateKeyPairTest(){
        String keyPairString = SM4Util.generateSecretKey();
        System.out.println(keyPairString);
    }

    /**
     * 加密，解密
     */
    @Test
    public void encryptAndDecryptTest(){
        String tempSm4Base64Content = SM4Util.encrypt(srcContent,secretKey);
        System.out.println(tempSm4Base64Content);
        String tempSrcContent = SM4Util.decrypt(tempSm4Base64Content, secretKey);
        System.out.println(tempSrcContent);
        Assertions.assertEquals(srcContent, tempSrcContent);
    }

    /**
     * 设置密码模式和填充算法加密，解密
     */
    @Test
    public void encryptAndDecryptTest2(){
        String tempSm4Base64Content = SM4Util.encrypt(srcContent,secretKey,KeyModeEnum.CBC, PaddingEnum.PKCS5_PADDING);
        System.out.println(tempSm4Base64Content);
        String tempSrcContent = SM4Util.decrypt(tempSm4Base64Content, secretKey,KeyModeEnum.CBC, PaddingEnum.PKCS5_PADDING);
        System.out.println(tempSrcContent);
        Assertions.assertEquals(srcContent, tempSrcContent);
    }

}

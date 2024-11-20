package net.somta.common.encrypt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SM2UtilTest {

    /**
     * 公钥
     */
    private final String publicKey = "BIRRyvkqK22Tu8JUj2fgDp0Hv4OGwcFmNOSZezaPWo50XVvhr/gSMxjw24ZBDvkEWxtYFM/AAjy29tS5UCANOCk=";

    /**
     * 私钥
     */
    private final String privateKey = "OA+4/b6efOq90DxBE47ftzgj2IhtV4e5n1WL7byi+84=";

    /**
     * 默认原始数据
     */
    private final String srcContent = "hello kernel";

    @Test
    public void generateKeyPairTest(){
        KeyPairString keyPairString = SM2Util.generateKeyPair();
        System.out.println(keyPairString.getPublicKey());
        System.out.println(keyPairString.getPrivateKey());
    }

    /**
     * 公钥加密，私钥解密
     */
    @Test
    public void encryptAndDecryptTest(){
        String tempSm2Base64Content = SM2Util.encrypt(srcContent,publicKey);
        System.out.println(tempSm2Base64Content);
        String tempSrcContent = SM2Util.decrypt(tempSm2Base64Content, privateKey);
        System.out.println(tempSrcContent);
        Assertions.assertEquals(srcContent, tempSrcContent);
    }

    /**
     * 私钥签名，公钥验签
     */
    @Test
    public void signAndVerifyByStringTest(){
        String signContent = SM2Util.sign(srcContent.getBytes(),privateKey);
        boolean verify = SM2Util.verify(srcContent.getBytes(),signContent,publicKey);
        System.out.println(verify);
        Assertions.assertTrue(verify);
    }

    /**
     * 私钥签名，公钥验签
     */
    @Test
    public void signAndVerifyTest(){
        byte[] signByte = SM2Util.signToByte(srcContent.getBytes(),privateKey);
        boolean verify = SM2Util.verify(srcContent.getBytes(),signByte,publicKey);
        System.out.println(verify);
        Assertions.assertTrue(verify);
    }

}

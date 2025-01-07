package net.somta.common.encrypt;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Objects;

/**
 * SM4算法加密工具类
 * 
 * @author lyon.chen
 * @date 2023/9/1
 */
public class SM4Util {

    /**
     * 默认密钥长度
     */
    public static final int DEFAULT_KEY_SIZE = 128;
    private static final String ALGORITHM_NAME = "SM4";
    private static final String DEFAULT_IV = "r4bk2e5XxSu4cy4S";

    static {
        if (Objects.isNull(Security.getProvider(BouncyCastleProvider.PROVIDER_NAME))) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * SM4 加密操作（默认采用PKCS5填充算法、ECB密码模式）
     *
     * @param content 待加密数据
     * @param secretKeyStr SM4密钥字符串
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String secretKeyStr) {
        return encrypt(content, secretKeyStr, KeyModeEnum.ECB, PaddingEnum.PKCS5_PADDING);
    }

    /**
     * SM4 加密操作
     * 
     * @param content 待加密数据
     * @param secretKeyStr SM4密钥字符串
     * @param keyMode 密码模式
     * @param padding 填充算法
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String secretKeyStr, KeyModeEnum keyMode, PaddingEnum padding) {
        byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
        byte[] secretKeyBytes = Base64Util.decodeToByte(secretKeyStr);
        byte[] result = encrypt(byteContent, secretKeyBytes, keyMode, padding);
        return Base64Util.encode(result);
    }

    /**
     * SM4 加密操作
     *
     * @param contentBytes 待加密数据byte[]
     * @param secretKeyBytes SM4密钥byte[]
     * @param keyMode 密码模式
     * @param padding 填充算法
     * @return 返回加密数据byte[]
     */
    public static byte[] encrypt(byte[] contentBytes, byte[] secretKeyBytes, KeyModeEnum keyMode, PaddingEnum padding) {
        try {
            Cipher cipher =
                Cipher.getInstance(String.format("%s/%s/%s", ALGORITHM_NAME, keyMode.getName(), padding.getName()),
                    BouncyCastleProvider.PROVIDER_NAME);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, ALGORITHM_NAME);
            if (KeyModeEnum.CBC.equals(keyMode)) {
                // 使用CBC模式，需要一个向量iv，增加加密算法的强度
                IvParameterSpec iv = new IvParameterSpec(DEFAULT_IV.getBytes());
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            }
            return cipher.doFinal(contentBytes);
        } catch (Exception e) {
            throw new RuntimeException("sm4 encrypt:" + e.getMessage());
        }
    }

    /**
     * SM4 解密操作（默认采用PKCS5填充算法、ECB密码模式）
     *
     * @param base64Content 待解密base64数据
     * @param secretKey SM4密钥
     * @return 解密后的原始数据
     */
    public static String decrypt(String base64Content, String secretKey) {
        return decrypt(base64Content, secretKey, KeyModeEnum.ECB, PaddingEnum.PKCS5_PADDING);
    }

    /**
     * SM4 解密操作
     *
     * @param base64Content 待解密base64数据
     * @param secretKey SM4密钥
     * @param keyMode 密码模式
     * @param padding 填充算法
     * @return 解密后的原始数据
     */
    public static String decrypt(String base64Content, String secretKey, KeyModeEnum keyMode,
        PaddingEnum padding) {
        byte[] byteContent = Base64Util.decodeToByte(base64Content);
        byte[] secretKeyBytes = Base64Util.decodeToByte(secretKey);
        byte[] result = decrypt(byteContent, secretKeyBytes, keyMode, padding);
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * SM4 解密操作
     *
     * @param contentBytes 待解密内容byte[]
     * @param secretKeyBytes SM4密钥byte[]
     * @param keyMode 密码模式
     * @param padding 填充算法
     * @return 返回解密数据byte[]
     */
    public static byte[] decrypt(byte[] contentBytes, byte[] secretKeyBytes, KeyModeEnum keyMode, PaddingEnum padding) {
        try {
            Cipher cipher =
                Cipher.getInstance(String.format("%s/%s/%s", ALGORITHM_NAME, keyMode.getName(), padding.getName()),
                    BouncyCastleProvider.PROVIDER_NAME);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, ALGORITHM_NAME);
            if (KeyModeEnum.CBC.equals(keyMode)) {
                // 使用CBC模式，需要一个向量iv，增加加密算法的强度
                IvParameterSpec iv = new IvParameterSpec(DEFAULT_IV.getBytes());
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            }
            return cipher.doFinal(contentBytes);
        } catch (Exception e) {
            throw new RuntimeException("sm4 decrypt error:"+e.getMessage());
        }
    }

    /**
     * 生成SM4密钥
     *
     * @return 返回Base64转码后的SM4密钥
     */
    public static String generateSecretKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
            // SM4要求128位密钥
            keyGenerator.init(DEFAULT_KEY_SIZE, new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64Util.encode(secretKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("sm4 generate secret key error:"+e.getMessage());
        }
    }

}

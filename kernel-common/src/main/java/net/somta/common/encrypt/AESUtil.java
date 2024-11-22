package net.somta.common.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 
 * AES对称加解密工具类
 * @author husong
 **/
public final class AESUtil {

    private static final String ALGORITHM_NAME = "AES";

    /**
     * 默认密钥长度
     */
    public static final int DEFAULT_KEY_SIZE = 128;

    /**
     * 默认的IV
     */
    private static final String DEFAULT_IV = "y4b42e5XxSu4cy4R";

    /**
     * AES 加密操作（默认采用PKCS5填充算法、ECB密码模式）
     *
     * @param content 待加密数据（Base64编码）
     * @param secretKey AES密钥（Base64编码）
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, SecretKey secretKey) {
        return encrypt(content, secretKey,KeyModeEnum.ECB);
    }

    /**
     * AES 加密操作
     * 
     * @param content 待加密数据（Base64编码）
     * @param secretKey AES密钥（Base64编码）
     * @param keyMode 密码模式
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, SecretKey secretKey,KeyModeEnum keyMode) {
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        byte[] result = encrypt(contentBytes, secretKey,keyMode,DEFAULT_IV.getBytes());
        return Base64Util.encode(result);
    }

    /**
     * AES 加密操作
     *
     * @param content 待加密数据（Base64编码）
     * @param secretKey AES密钥（Base64编码）
     * @param keyMode 密码模式
     * @param ivStr IV字符串
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, SecretKey secretKey,KeyModeEnum keyMode,String ivStr) {
        byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
        byte[] result = encrypt(contentBytes, secretKey,keyMode,ivStr.getBytes());
        return Base64Util.encode(result);
    }

    /**
     * AES 加密操作
     *
     * @param contentBytes 待加密数据byte[]
     * @param secretKey AES密钥对象
     * @param keyMode 密码模式
     * @param ivByte IV字节数组
     * @return 返回加密数据byte[]
     */
    public static byte[] encrypt(byte[] contentBytes, SecretKey secretKey,KeyModeEnum keyMode,byte[] ivByte) {
        try {
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/PKCS5Padding", ALGORITHM_NAME, keyMode.getName()));
            if (KeyModeEnum.CBC.equals(keyMode)) {
                // 使用CBC模式，需要一个向量iv，增加加密算法的强度
                IvParameterSpec iv = new IvParameterSpec(ivByte);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }
            return cipher.doFinal(contentBytes);
        } catch (Exception e) {
            throw new RuntimeException("AES "+e.getMessage());
        }
    }

    /**
     * AES 解密操作
     *
     * @param base64Content 待解密base64数据
     * @param secretKey AES密钥
     * @return 解密后的原始数据
     */
    public static String decrypt(String base64Content, SecretKey secretKey) {
        byte[] contentBytes = Base64Util.decodeToByte(base64Content);
        byte[] result = decrypt(contentBytes, secretKey,KeyModeEnum.ECB,DEFAULT_IV.getBytes());
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * AES 解密操作
     *
     * @param base64Content 待解密base64数据
     * @param secretKey AES密钥
     * @param keyMode 密码模式
     * @return 解密后的原始数据
     */
    public static String decrypt(String base64Content, SecretKey secretKey,KeyModeEnum keyMode) {
        byte[] contentBytes = Base64Util.decodeToByte(base64Content);
        byte[] result = decrypt(contentBytes, secretKey,keyMode,DEFAULT_IV.getBytes());
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * AES 解密操作
     *
     * @param base64Content 待解密base64数据
     * @param secretKey AES密钥
     * @param keyMode 密码模式
     * @param ivStr IV字符串
     * @return 解密后的原始数据
     */
    public static String decrypt(String base64Content, SecretKey secretKey,KeyModeEnum keyMode,String ivStr) {
        byte[] contentBytes = Base64Util.decodeToByte(base64Content);
        byte[] result = decrypt(contentBytes, secretKey,keyMode,ivStr.getBytes());
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * AES 解密操作
     *
     * @param contentBytes 待解密内容byte[]
     * @param secretKey AES密钥
     * @param keyMode 密码模式
     * @param ivByte IV字节数组
     * @return 返回解密数据byte[]
     */
    public static byte[] decrypt(byte[] contentBytes, SecretKey secretKey,KeyModeEnum keyMode,byte[] ivByte) {
        try {
            Cipher cipher = Cipher.getInstance(String.format("%s/%s/PKCS5Padding", ALGORITHM_NAME, keyMode.getName()));
            if (KeyModeEnum.CBC.equals(keyMode)) {
                // 使用CBC模式，需要一个向量iv，增加加密算法的强度
                IvParameterSpec iv = new IvParameterSpec(ivByte);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            }
            return cipher.doFinal(contentBytes);
        } catch (Exception e) {
            throw new RuntimeException("AES decrypt exception:"+e.getMessage());
        }
    }

    /**
     * 生成AES秘钥
     *
     * @param keySize 密钥长度（位数）
     * @return 返回Base64转码后的AES密钥
     */
    public static SecretKey generateSecretKey(int keySize) {
        try {
            // AES密钥长度最低要求为128位
            keySize = Math.max(keySize, DEFAULT_KEY_SIZE);

            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_NAME);
            keyGenerator.init(keySize, new SecureRandom());
            return keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("generate secret exception:"+e.getMessage());
        }
    }

    /**
     * 将字符串转换为AES密钥对象
     * @param secretKeyStr 密钥字符串
     * @return 密钥对象
     */
    public static SecretKeySpec stringToSecretKey(String secretKeyStr) {
        if (secretKeyStr == null) {
            throw new IllegalArgumentException("Key string cannot be null");
        }
        int keyLength = secretKeyStr.length();
        if (keyLength != DEFAULT_KEY_SIZE / 8 && keyLength != DEFAULT_KEY_SIZE * 2 / 8) {
            throw new IllegalArgumentException("Key string must be exactly " + DEFAULT_KEY_SIZE / 8 + " or " + DEFAULT_KEY_SIZE*2 / 8 + " characters long");
        }
        byte[] keyBytes = secretKeyStr.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, ALGORITHM_NAME);
    }

}
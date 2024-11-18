package net.somta.common.encrypt;

import org.bouncycastle.asn1.gm.GMNamedCurves;
import org.bouncycastle.asn1.x9.X9ECParameters;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.engines.SM2Engine;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.signers.SM2Signer;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Objects;

/**
 * SM2非对称加密工具类,公钥加密，私钥解密
 * 
 * @author husong
 */
public class SM2Util {

    private static final Logger LOGGER = LoggerFactory.getLogger(SM2Util.class);

    private static final X9ECParameters X_9_EC_PARAMETERS = GMNamedCurves.getByName("sm2p256v1");

    static {
        if (Objects.isNull(Security.getProvider(BouncyCastleProvider.PROVIDER_NAME))) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 生成密钥对
     * @return 密钥对
     */
    public static KeyPairString generateKeyPair() {
        try {
            ECKeyPairGenerator keyPairGenerator = new ECKeyPairGenerator();

            ECDomainParameters ecDomainParameters = new ECDomainParameters(X_9_EC_PARAMETERS.getCurve(),
                X_9_EC_PARAMETERS.getG(), X_9_EC_PARAMETERS.getN());
            ECKeyGenerationParameters ecKeyGenerationParameters =
                new ECKeyGenerationParameters(ecDomainParameters, SecureRandom.getInstance("SHA1PRNG"));
            keyPairGenerator.init(ecKeyGenerationParameters);

            AsymmetricCipherKeyPair asymmetricCipherKeyPair = keyPairGenerator.generateKeyPair();
            BigInteger bigInteger = ((ECPrivateKeyParameters)asymmetricCipherKeyPair.getPrivate()).getD();
            String privateKeyString = Base64Util.encode(bigInteger.toByteArray());

            ECPoint ecPoint = ((ECPublicKeyParameters)asymmetricCipherKeyPair.getPublic()).getQ();
            String publicKeyString = Base64Util.encode(ecPoint.getEncoded(false));

            return new KeyPairString(privateKeyString, publicKeyString);
        } catch (Exception e) {
            throw new RuntimeException("generateKeyPair error:"+e.getMessage());
        }
    }

    /**
     * SM2 加密操作
     *
     * @param content 待加密内容字符串
     * @param publicKey 加密publicKey
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String publicKey) {
        byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
        byte[] result = encrypt(byteContent, publicKey);
        return Base64Util.encode(result);
    }

    /**
     * SM2 加密操作
     *
     * @param byteContent 待加密内容byte[]
     * @param publicKey 加密publicKey
     * @return 返回加密数据byte[]
     */
    public static byte[] encrypt(byte[] byteContent, String publicKey) {
        try {
            byte[] encoded = Base64Util.decodeToByte(publicKey);
            ECDomainParameters ecDomainParameters = new ECDomainParameters(X_9_EC_PARAMETERS.getCurve(),
                X_9_EC_PARAMETERS.getG(), X_9_EC_PARAMETERS.getN());
            ECPoint ecPoint = ecDomainParameters.getCurve().decodePoint(encoded);
            SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
            sm2Engine.init(true,new ParametersWithRandom(new ECPublicKeyParameters(ecPoint, ecDomainParameters), new SecureRandom()));
            return sm2Engine.processBlock(byteContent, 0, byteContent.length);
        } catch (Exception e) {
            throw new RuntimeException("sm2 encrypt:" + e.getMessage());
        }
    }

    /**
     * SM2 解密操作
     *
     * @param base64Content 待解密base64内容
     * @param privateKey 加密privateKey
     * @return 解密后的原文字符串
     */
    public static String decrypt(String base64Content, String privateKey) {
        byte[] byteContent = Base64Util.decodeToByte(base64Content);
        byte[] result = decrypt(byteContent, privateKey);
        return new String(result, StandardCharsets.UTF_8);
    }

    /**
     * SM2 解密操作
     *
     * @param byteContent 待解密byte[]
     * @param privateKey 解密privateKey
     * @return 返回解密数据byte[]
     */
    public static byte[] decrypt(byte[] byteContent, String privateKey) {
        try {
            byte[] decoded = Base64Util.decodeToByte(privateKey);
            ECDomainParameters ecDomainParameters = new ECDomainParameters(X_9_EC_PARAMETERS.getCurve(),
                X_9_EC_PARAMETERS.getG(), X_9_EC_PARAMETERS.getN());
            SM2Engine sm2Engine = new SM2Engine(SM2Engine.Mode.C1C3C2);
            sm2Engine.init(false, new ECPrivateKeyParameters(new BigInteger(decoded), ecDomainParameters));
            return sm2Engine.processBlock(byteContent, 0, byteContent.length);
        } catch (Exception e) {
            throw new RuntimeException("sm2 decrypt:" + e.getMessage());
        }
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @link 使用verify()方法验签
     *
     * @param data 待签名数据，须转成二进制格式
     * @param privateKey SM2私钥
     * @return 签名
     */
    public static String sign(byte[] data, String privateKey) {
        byte[] signBytes = signToByte(data, privateKey);
        return Base64Util.encode(signBytes);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @link 使用verify()方法验签
     *
     * @param data 待签名数据，须转成二进制格式
     * @param privateKey SM2私钥
     * @return 签名数组
     */
    public static byte[] signToByte(byte[] data, String privateKey) {
        try {
            ECDomainParameters ecDomainParameters = new ECDomainParameters(X_9_EC_PARAMETERS.getCurve(),
                X_9_EC_PARAMETERS.getG(), X_9_EC_PARAMETERS.getN());
            ECPrivateKeyParameters privateKeyParameters =
                new ECPrivateKeyParameters(new BigInteger(Base64Util.decodeToByte(privateKey)), ecDomainParameters);

            SM2Signer sm2Signer = new SM2Signer();
            sm2Signer.init(true,
                new ParametersWithID(
                    new ParametersWithRandom(privateKeyParameters, SecureRandom.getInstance("SHA1PRNG")),
                    Strings.toByteArray("2014567812345678")));
            sm2Signer.update(data, 0, data.length);

            return sm2Signer.generateSignature();
        } catch (Exception e) {
            throw new RuntimeException("sm2:"+e.getMessage());
        }
    }

    /**
     * 校验数字签名
     * 使用sign()签名后，可用此方法验签
     *
     * @param data 待签名数据
     * @param sign SM2私钥签名
     * @param publicKey SM2公钥
     * @return 验签成功返回true, 否则返回false
     */
    public static boolean verify(byte[] data, String sign, String publicKey) {
        return verify(data, Base64Util.decodeToByte(sign), publicKey);
    }

    /**
     * 校验数字签名
     * 使用signToByte()签名后，可用此方法验签
     *
     * @param data 未签名原始数据,
     * @param sign SM2私钥签名的签名数据
     * @param publicKey SM2公钥
     * @return 验签成功返回true, 否则返回false
     */
    public static boolean verify(byte[] data, byte[] sign, String publicKey) {
        try {
            ECDomainParameters domainParameters = new ECDomainParameters(X_9_EC_PARAMETERS.getCurve(),
                X_9_EC_PARAMETERS.getG(), X_9_EC_PARAMETERS.getN());
            // 提取公钥点
            ECPoint pukPoint = X_9_EC_PARAMETERS.getCurve().decodePoint(Base64Util.decodeToByte(publicKey));
            ECPublicKeyParameters publicKeyParameters = new ECPublicKeyParameters(pukPoint, domainParameters);

            SM2Signer sm2Signer = new SM2Signer();
            sm2Signer.init(false, new ParametersWithID(publicKeyParameters, Strings.toByteArray("2014567812345678")));
            sm2Signer.update(data, 0, data.length);
            return sm2Signer.verifySignature(sign);
        } catch (Exception e) {
            LOGGER.error("SM2 签名错误：", e);
            return Boolean.FALSE;
        }
    }
}

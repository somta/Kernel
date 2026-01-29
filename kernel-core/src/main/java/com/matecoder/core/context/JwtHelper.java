package com.matecoder.core.context;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author husong
 * @since 3.1.1
 */
public class JwtHelper {
    // 默认签名密钥
    private static final byte[] DEFAULT_SIGN_KEY_BYTES = "www.matecoder.com##www.somta.net".getBytes(StandardCharsets.UTF_8);
    // 默认过期时间（小时）
    private static final Integer DEFAULT_EXPIRE_HOUR = 2;
    // JWT 算法
    private static final JWSAlgorithm JWT_ALGORITHM = JWSAlgorithm.HS256;

    /**
     * 生成token（默认密钥+默认过期时间）
     * @param issuer   签发者
     * @param payload  载荷,需要在token中存放的数据
     * @return token字符串
     */
    public static String generateToken(String issuer, Map<String, Object> payload) {
        return generateToken(issuer, payload, null, DEFAULT_EXPIRE_HOUR);
    }

    /**
     * 生成token（自定义密钥+默认过期时间）
     * @param issuer     签发者
     * @param payload    载荷,需要在token中存放的数据
     * @param signKeyStr 签名的密钥字符串
     * @return token字符串
     */
    public static String generateToken(String issuer, Map<String, Object> payload, String signKeyStr) {
        return generateToken(issuer, payload, signKeyStr, DEFAULT_EXPIRE_HOUR);
    }

    /**
     * 生成token（自定义密钥+自定义过期时间）
     * @param issuer     签发者
     * @param payload    载荷,需要在token中存放的数据
     * @param signKeyStr 签名的密钥字符串
     * @param expireHour token过期时间,单位小时
     * @return token字符串
     */
    public static String generateToken(String issuer, Map<String, Object> payload, String signKeyStr, Integer expireHour) {
        try {
            // 1. 构建JWS头部（对应原header，保持typ=JWT、alg=HS256）
            JWSHeader jwsHeader = new JWSHeader.Builder(JWT_ALGORITHM)
                    .type(JOSEObjectType.JWT)
                    .build();

            // 2. 计算过期时间
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.HOUR, expireHour);
            Date expireDate = instance.getTime();

            // 3. 构建JWT Claims（对应原payload）
            JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                    .issuer(issuer)          // 签发者
                    .issueTime(new Date())   // 发行时间
                    .expirationTime(expireDate); // 过期时间

            // 4. 添加自定义载荷
            if (payload != null && !payload.isEmpty()) {
                for (Map.Entry<String, Object> entry : payload.entrySet()) {
                    claimsBuilder.claim(entry.getKey(), entry.getValue());
                }
            }
            JWTClaimsSet claimsSet = claimsBuilder.build();

            // 5. 创建签名器并生成Token
            SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
            MACSigner signer = new MACSigner(getSignKeyBytes(signKeyStr));
            signedJWT.sign(signer);

            // 6. 序列化为字符串（对应原compact()）
            return signedJWT.serialize();

        } catch (JOSEException e) {
            throw new RuntimeException("生成JWT Token失败", e);
        }
    }

    /**
     * 校验token是否过期
     * @param token      token数据
     * @param signKeyStr 签名密钥
     * @return true:过期/无效  false：未过期/有效
     */
    public static Boolean verifyExpired(String token, String signKeyStr) {
        if (StringUtils.isEmpty(token)) {
            return true;
        }
        try {
            // 解析并验证Token
            JWTClaimsSet claimsSet = getJWTClaimsSet(token, signKeyStr);
            // 检查过期时间
            return claimsSet.getExpirationTime().before(new Date());
        } catch (Exception e) {
            // 任何异常都视为过期/无效
            return true;
        }
    }

    /**
     * 解析token
     * @param token      token数据
     * @param signKeyStr 签名密钥
     * @return 身份上下文
     */
    public static IdentityContext parseToken(String token, String signKeyStr) {
        try {
            JWTClaimsSet claimsSet = getJWTClaimsSet(token, signKeyStr);
            if (claimsSet == null) {
                return null;
            }
            Long userId = Long.valueOf(String.valueOf(claimsSet.getClaim(IdentityContext.USER_ID)));
            Long tenantId = Long.valueOf(String.valueOf(claimsSet.getClaim(IdentityContext.TENANT_ID)));
            Map<String, String> extend = (Map<String, String>) claimsSet.getClaim(IdentityContext.EXTEND);

            return new IdentityContext(userId, tenantId, extend);
        } catch (Exception e) {
            throw new RuntimeException("解析JWT Token失败", e);
        }
    }

    /**
     * 解析并验证Token，获取Claims（核心私有方法）
     * @param token      token数据
     * @param signKeyStr 签名密钥
     * @return JWTClaimsSet
     * @throws Exception 解析/验证异常
     */
    private static JWTClaimsSet getJWTClaimsSet(String token, String signKeyStr) throws Exception {
        // 1. 解析SignedJWT
        SignedJWT signedJWT = SignedJWT.parse(token);

        // 2. 验证签名（对应原verifyWith）
        MACVerifier verifier = new MACVerifier(getSignKeyBytes(signKeyStr));
        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("JWT Token签名验证失败");
        }

        // 3. 验证算法（防止算法篡改）
        if (!JWT_ALGORITHM.equals(signedJWT.getHeader().getAlgorithm())) {
            throw new JOSEException("JWT Token算法不匹配，预期HS256");
        }

        // 4. 返回Claims（对应原getPayload）
        return signedJWT.getJWTClaimsSet();
    }

    /**
     * 获取签名密钥字节数组
     * @param signKeyStr 密钥字符串
     * @return 密钥字节数组
     */
    private static byte[] getSignKeyBytes(String signKeyStr) {
        if (StringUtils.isBlank(signKeyStr)) {
            return DEFAULT_SIGN_KEY_BYTES;
        } else {
            return signKeyStr.getBytes(StandardCharsets.UTF_8);
        }
    }
}
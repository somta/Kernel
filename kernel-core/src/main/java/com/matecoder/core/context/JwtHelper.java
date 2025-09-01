package com.matecoder.core.context;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author husong
 * @since 3.1.1
 */
public class JwtHelper {
    private static final SecretKey DEFAULT_SIGN_KEY = Keys.hmacShaKeyFor("www.matecoder.com##www.somta.net".getBytes(StandardCharsets.UTF_8));

    private static final Integer DEFAULT_EXPIRE_HOUR = 2;

    /**
     * 生成token
     * @param issuer   签发者
     * @param payload 载荷,需要在token中存放的数据
     * @return token字符串
     */
    public static String generateToken(String issuer, Map<String, Object> payload) {
        return generateToken(issuer, payload, null, DEFAULT_EXPIRE_HOUR);
    }

    /**
     * 生成token
     * @param issuer   签发者
     * @param payload 载荷,需要在token中存放的数据
     * @param signKeyStr 签名的密钥字符串
     * @return token字符串
     */
    public static String generateToken(String issuer, Map<String, Object> payload,String signKeyStr) {
        return generateToken(issuer, payload, signKeyStr, DEFAULT_EXPIRE_HOUR);
    }

    /**
     * 生成token
     * @param issuer   签发者
     * @param payload 载荷,需要在token中存放的数据
     * @param signKeyStr 签名的密钥字符串
     * @param expireHour token过期时间,单位小时
     * @return token字符串
     */
    public static String generateToken(String issuer, Map<String, Object> payload,String signKeyStr,Integer expireHour) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, expireHour);
        Date expireDate = instance.getTime();
        return Jwts.builder()
                // 设置头部信息header
                .header()
                    .add("typ", "JWT")
                    .add("alg", "HS256")
                    .and()
                // 设置自定义负载信息payload
                .claims()
                    .add(payload)
                    .and()
                .issuer(issuer)
                //发行时间
                .issuedAt(new Date())
                //过期时间
                .expiration(expireDate)
                .signWith(getSignKey(signKeyStr))
                .compact();
    }
 
    /**
     * 校验token是否过期
     * @param token token数据
     * @return ture:过期  false：未过期
     */
    public static Boolean verifyExpired(String token,String signKeyStr) {
        if(StringUtils.isEmpty(token)){
            return true;
        }
        try {
            Claims claimsJws = getClaimsJws(token,signKeyStr);
            return claimsJws.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 解析token
     * @param token token数据
     * @return 身份上下文
     */
    public static IdentityContext parseToken(String token, String signKeyStr) {
        Claims claimsJws = getClaimsJws(token,signKeyStr);
        IdentityContext identityContext = null;
        if(claimsJws != null){
            Long userId = Long.valueOf(String.valueOf(claimsJws.get(IdentityContext.USER_ID)));
            Long tenantId = Long.valueOf(String.valueOf(claimsJws.get(IdentityContext.TENANT_ID)));
            Map<String, String> extend = (Map<String, String>) claimsJws.get(IdentityContext.EXTEND);
            identityContext = new IdentityContext(userId,tenantId,extend);
        }
        return identityContext;
    }

    /**
     * 获取ClaimsJws
     * @param token token数据
     * @return Claims
     */
    private static Claims getClaimsJws(String token,String signKeyStr) {
        JwtParserBuilder jwtParserBuilder = Jwts.parser();
        //设置签名的密钥
        jwtParserBuilder.verifyWith(getSignKey(signKeyStr));
        //解析内容,获得payload
        return jwtParserBuilder.build().parseSignedClaims(token).getPayload();
    }

    /**
     * 获取签名的私钥
     * @param signKeyStr 密钥字符串
     * @return 密钥
     */
    private static SecretKey getSignKey(String signKeyStr) {
        SecretKey signKey;
        if(StringUtils.isBlank(signKeyStr)){
            signKey = DEFAULT_SIGN_KEY;
        }else {
            signKey = Keys.hmacShaKeyFor(signKeyStr.getBytes(StandardCharsets.UTF_8));
        }
        return signKey;
    }

}
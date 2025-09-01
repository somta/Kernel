package com.matecoder.core.context;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class JwtHelperTest {

    @Test
    public void generateTokenTest() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", 1L);
        payload.put("tenantId", 8888L);
        String token = JwtHelper.generateToken("matecoder",payload, null);
        System.out.println(token);
    }

    @Test
    public void verifyExpiredTest() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", 1L);
        payload.put("tenantId", 8888L);
        String token = JwtHelper.generateToken("matecoder",payload, null);
        System.out.println(token);
        Boolean verifyExpired = JwtHelper.verifyExpired(token, null);
        System.out.println(verifyExpired);
    }

    @Test
    public void parseTokenTest() throws Exception {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", 1L);
        payload.put("tenantId", 8888L);
        String token = JwtHelper.generateToken("matecoder",payload, null);
        IdentityContext identityContext = JwtHelper.parseToken(token, null);
        System.out.println(identityContext.getTenantId());
        System.out.println(identityContext.getUserId());
        System.out.println(identityContext.getExtend());
    }
}

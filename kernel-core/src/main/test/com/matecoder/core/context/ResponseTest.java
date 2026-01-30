package com.matecoder.core.context;

import com.matecoder.core.protocol.ResponseDataResult;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ResponseTest {

    @Test
    public void dataResultTest() throws Exception {
        ResponseDataResult<Map<String, Object>> result = dataResult();
        System.out.println( result);
    }


    private ResponseDataResult<Map<String, Object>> dataResult() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("userId", 1L);
        payload.put("tenantId", 8888L);
        return ResponseDataResult.setResponseResult(payload);
    }
}

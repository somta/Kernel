package net.somta.common.utils.httpclient;

import net.somta.core.protocol.ResponseDataResult;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

public class ResponseHelper {

    public static ResponseDataResult buildResponse(CloseableHttpResponse response, ResponseDataResult responseDataResult) throws IOException, ParseException {
        String httpStr = null;
        if (response != null) {
            int statusCode = response.getCode();
            HttpEntity entity = response.getEntity();
            if (statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
                responseDataResult.setSuccess(false);
                responseDataResult.setErrorCode(statusCode);
                responseDataResult.setErrorMsg(response.getReasonPhrase());
                return responseDataResult;
            }
            if(entity != null){
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
        }
        responseDataResult.setSuccess(true);
        responseDataResult.setResult(httpStr);
        return responseDataResult;
    }

}

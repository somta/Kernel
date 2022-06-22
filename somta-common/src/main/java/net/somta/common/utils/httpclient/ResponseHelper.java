package net.somta.common.utils.httpclient;

import net.somta.core.base.result.ResponseDataResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ResponseHelper {

    public static ResponseDataResult buildResponse(CloseableHttpResponse response, ResponseDataResult responseDataResult) throws IOException {
        String httpStr = null;
        if (response != null) {
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (statusCode >= HttpStatus.SC_MULTIPLE_CHOICES) {
                responseDataResult.setSuccess(false);
                responseDataResult.setErrorCode(String.valueOf(statusCode));
                responseDataResult.setErrorMessage(response.getStatusLine().getReasonPhrase());
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

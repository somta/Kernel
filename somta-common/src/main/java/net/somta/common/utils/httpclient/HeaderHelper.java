package net.somta.common.utils.httpclient;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClient 请求头的相关操作
 * Blog: https://www.somta.net/
 * Date: 2020/1/20
 * @author 明天的地平线
 * @version 1.0.0
 */
public class HeaderHelper {

    /**
     * 填充请求头参数至get请求中
     * @param httpPost 请求对象
     * @param headers 请求头
     * @return HttpGet
     */
    public static HttpGet setHeadersToGet(HttpGet httpPost, Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            for (String key : headers.keySet()) {
                httpPost.addHeader(key, headers.get(key));
            }
        }
        return httpPost;
    }


    /**
     * 填充请求头参数至Post请求中
     * @param httpPost HttpPost
     * @param headers 请求头
     * @return HttpPost
     */
    public static HttpPost setHeadersToPost(HttpPost httpPost, Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            for (String key : headers.keySet()) {
                httpPost.addHeader(key, headers.get(key));
            }
        }
        return httpPost;
    }


    /**
     * 填充请求头参数至Put请求中
     * @param httpPut HttpPut
     * @param headers 请求头
     * @return HttpPut
     */
    public static HttpPut setHeadersToPut(HttpPut httpPut, Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            for (String key : headers.keySet()) {
                httpPut.addHeader(key, headers.get(key));
            }
        }
        return httpPut;
    }

    /**
     * 填充请求头参数至Delete请求中
     * @param httpDelete HttpPut
     * @param headers 请求头
     * @return HttpPut
     */
    public static HttpDelete setHeadersToDelete(HttpDelete httpDelete, Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            for (String key : headers.keySet()) {
                httpDelete.addHeader(key, headers.get(key));
            }
        }
        return httpDelete;
    }

    /**
     * 填充请求体参数至Post请求中
     * @param httpPost HttpPost
     * @param params 请求参数
     * @return HttpPost
     */
    public static HttpPost setParamsToRequest(HttpPost httpPost, Map<String, Object> params) {

        List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            NameValuePair pair = null;
            if (entry.getValue() == null) {
                pair = new BasicNameValuePair(entry.getKey(), null);
            } else {
                pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
            }
            pairList.add(pair);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
        return httpPost;
    }

}

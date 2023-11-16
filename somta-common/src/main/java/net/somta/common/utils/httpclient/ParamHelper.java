package net.somta.common.utils.httpclient;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.classic.methods.HttpUriRequestBase;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;


import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author husong
 */
public class ParamHelper {


    /**
     * 构建GET请求参数
     * @param httpRequest http
     * @param url v1/user/info
     * @param params name=gavin
     */
    public static void buildFormParams(HttpUriRequestBase httpRequest, String url, Map<String, Object> params){
        List<NameValuePair> nvps = new ArrayList<>();
        if (params != null) {
            for (Map.Entry<String, Object> param : params.entrySet()) {
                nvps.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
            }
            try {
                URI uri = new URIBuilder(new URI(url))
                        .addParameters(nvps)
                        .build();
                httpRequest.setUri(uri);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 填充请求体参数至Post请求中
     * @param httpPost HttpPost
     * @param params 请求参数
     * @return HttpPost
     */
    public static HttpPost setParamsToPost(HttpPost httpPost, Map<String, Object> params) {
        if(params == null){
            return httpPost;
        }
        List<NameValuePair> pairList = new ArrayList<>(params.size());
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

    /**
     * 填充请求体参数至Put请求中
     * @param httpPut httpPut
     * @param params 请求参数
     * @return HttpPost
     */
    public static HttpPut setParamsToPut(HttpPut httpPut, Map<String, Object> params) {

        List<NameValuePair> pairList = new ArrayList<>(params.size());
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
        httpPut.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName("UTF-8")));
        return httpPut;
    }


    /*public static void main(String[] args) {
        Map<String, Object> params = new HashMap<>();
        params.put("name","husong");
        params.put("age",18);
        buildGetParams("v1/name",params);
    }*/

}

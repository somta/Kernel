package net.somta.common.utils.httpclient;

import net.somta.core.protocol.ResponseDataResult;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.util.Timeout;


import java.io.IOException;
import java.util.*;

public class HttpClientUtil {

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static CloseableHttpClient httpClient;
    private static final int MAX_TIMEOUT = 7000;
    private static final String ENCODING_CODE = "UTF-8";

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectionRequestTimeout(Timeout.ofMilliseconds(MAX_TIMEOUT));
        // 设置读取超时
        configBuilder.setResponseTimeout(Timeout.ofMilliseconds(MAX_TIMEOUT));
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(Timeout.ofMilliseconds(MAX_TIMEOUT));
        // 在提交请求之前 测试连接是否可用
        configBuilder.setContentCompressionEnabled(true);
        httpClient = HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(configBuilder.build()).build();
    }

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     * @param url 请求地址
     * @return ResponseDataResult
     */
    public static ResponseDataResult doGet(String url) {
        return doGet(url, new HashMap<>(), new HashMap<>());
    }

    /**
     * 发送 GET 请求（HTTP）,K-V形式,无请求头参数
     * @param url 请求地址
     * @param params 请求参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doGet(String url, Map<String, Object> params) {
        return doGet(url, params, null);
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式，有请求头参数
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doGet(String url, Map<String, Object> params, Map<String, String> headers) {
        ResponseDataResult responseDataResult = new ResponseDataResult();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            ParamHelper.buildFormParams(httpGet,url,params);
            httpGet = HeaderHelper.setHeadersToGet(httpGet, headers);
            response = httpClient.execute(httpGet);

            return ResponseHelper.buildResponse(response,responseDataResult);

        } catch (Exception e) {
            responseDataResult.setSuccess(false);
            responseDataResult.setErrorMsg(e.getMessage());
            return responseDataResult;
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * 发送 POST 请求（HTTP），不带输入数据
     * 参数为application/x-www-form-urlencoded的K-V形式,无请求头参数
     * @param url 请求参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doFormPost(String url) {
        return doFormPost(url, null);
    }

    /**
     * 发送 POST 请求（HTTP）,
     * 参数为application/x-www-form-urlencoded的K-V形式,无请求头参数
     * @param url 请求地址
     * @param params 请求参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doFormPost(String url, Map<String, Object> params) {
        return doFormPost(url, params, null);
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式 ，有请求头参数
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doFormPost(String url, Map<String, Object> params, Map<String, String> headers) {
        ResponseDataResult responseDataResult = new ResponseDataResult();
        HttpPost httpPost = new HttpPost(url);
        ParamHelper.buildFormParams(httpPost,url,params);
        CloseableHttpResponse response = null;
        try {
            httpPost.setConfig(requestConfig);
            httpPost = HeaderHelper.setHeadersToPost(httpPost, headers);
            response = httpClient.execute(httpPost);
            return ResponseHelper.buildResponse(response,responseDataResult);
        } catch (Exception e) {
            responseDataResult.setSuccess(false);
            responseDataResult.setErrorMsg(e.getMessage());
            return responseDataResult;
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 发送 POST 请求（HTTP），
     * 参数为JSON形式，无请求参数，无请求头参数
     * @param url 请求地址
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPost(String url) {
        return doPost(url, null);
    }

    /**
     * 发送 POST 请求（HTTP），
     * 参数为JSON形式，无请求头参数
     * @param url 请求地址
     * @param json json对象
     * @return ResponseDataResult
     */
   public static ResponseDataResult doPost(String url, String json) {
        return doPost(url, json, null);
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式，有请求头参数
     * @param url 请求地址
     * @param json    json对象
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
   public static ResponseDataResult doPost(String url, String json, Map<String, String> headers) {
        ResponseDataResult responseDataResult = new ResponseDataResult();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            httpPost = HeaderHelper.setHeadersToPost(httpPost, headers);
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);;
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            return ResponseHelper.buildResponse(response,responseDataResult);
        } catch (Exception e) {
            responseDataResult.setSuccess(false);
            responseDataResult.setErrorMsg(e.getMessage());
            return responseDataResult;
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 发送 PUT 请求（HTTP），K-V形式，无参数，无请求头参数
     * @param url API接口URL
     * @return ResponseDataResult
     */
    public static ResponseDataResult doFormPut(String url) {
        return doFormPut(url, null);
    }

    /**
     * 发送 PUT 请求（HTTP），K-V形式，无请求头参数
     * @param url    API接口URL
     * @param params 参数map
     * @return ResponseDataResult
     */
    public static ResponseDataResult doFormPut(String url, Map<String, Object> params) {
        return doFormPut(url, params, null);
    }

    /**
     * 发送 PUT 请求（HTTP），K-V形式，有请求头参数
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doFormPut(String url, Map<String, Object> params, Map<String, String> headers) {
        ResponseDataResult responseDataResult = new ResponseDataResult();
        HttpPut httpPut = new HttpPut(url);
        CloseableHttpResponse response = null;
        try {
            HeaderHelper.setHeadersToPut(httpPut,headers);
            ParamHelper.setParamsToPut(httpPut,params);
            response = httpClient.execute(httpPut);
            return ResponseHelper.buildResponse(response,responseDataResult);
        } catch (Exception e) {
            responseDataResult.setSuccess(false);
            responseDataResult.setErrorMsg(e.getMessage());
            return responseDataResult;
        } finally {
            try {
                if (response != null) {
                    EntityUtils.consume(response.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 发送 PUT 请求（HTTP），JSON形式，无请求头参数
     * @param url  API接口URL
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPut(String url) {
        return doPut(url, null);
    }

    /**
     * 发送 PUT 请求（HTTP），JSON形式，无请求头参数
     * @param url  API接口URL
     * @param json JSON对象
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPut(String url, String json) {
        return doPut(url, json, null);
    }

    /**
     * 发送 PUT 请求（HTTP），JSON形式，有请求头参数
     * @param url     API接口URL
     * @param json    JSON对象
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPut(String url, String json, Map<String, String> headers) {
        ResponseDataResult responseDataResult = new ResponseDataResult();
        HttpPut httpPut = new HttpPut(url);
        CloseableHttpResponse response = null;
        try {
            httpPut = HeaderHelper.setHeadersToPut(httpPut, headers);
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);;
            httpPut.setEntity(stringEntity);
            response = httpClient.execute(httpPut);
            return ResponseHelper.buildResponse(response,responseDataResult);
        } catch (Exception e) {
            responseDataResult.setSuccess(false);
            responseDataResult.setErrorMsg(e.getMessage());
            return responseDataResult;
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 发送 DELETE 请求（HTTP）,无参数
     * @param url API接口URL
     * @return ResponseDataResult
     */
    public static ResponseDataResult doDelete(String url) {
        return doDelete(url, null);
    }

    /**
     * 发送 DELETE 请求（HTTP），K-V形式，无请求头参数
     * @param url    API接口URL
     * @param params 参数map
     * @return ResponseDataResult
     */
    public static ResponseDataResult doDelete(String url, Map<String, Object> params) {
        return doDelete(url, params, null);
    }

    /**
     * 发送 DELETE 请求（HTTP），K-V形式，有请求头参数
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doDelete(String url, Map<String, Object> params, Map<String, String> headers) {
        ResponseDataResult responseDataResult = new ResponseDataResult();
        CloseableHttpResponse response = null;
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            HeaderHelper.setHeadersToDelete(httpDelete,headers);
            ParamHelper.buildFormParams(httpDelete,url,params);
            response = httpClient.execute(httpDelete);
            return ResponseHelper.buildResponse(response,responseDataResult);
        } catch (Exception e) {
            responseDataResult.setSuccess(false);
            responseDataResult.setErrorMsg(e.getMessage());
            return responseDataResult;
        } finally {
            try {
                if (response != null) {
                    EntityUtils.consume(response.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

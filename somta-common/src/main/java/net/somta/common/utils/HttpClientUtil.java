package net.somta.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import net.somta.core.base.result.ResponseDataResult;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


/**
 * Http请求工具
 * Blog: https://www.somta.net/
 * Date: 2020/1/20
 * @author 明天的地平线
 * @version 1.0.0
 */
@Deprecated
public final class HttpClientUtil {

	private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 7000;
    private static final String CODE = "UTF-8";

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 在提交请求之前 测试连接是否可用
        configBuilder.setStaleConnectionCheckEnabled(true);
        requestConfig = configBuilder.build();
    }

    /**
     * 填充请求头参数至get请求中
     * @param httpPost 请求对象
     * @param headers 请求头
     * @return HttpGet
     */
    private static HttpGet setHeadersToGet(HttpGet httpPost, Map<String, String> headers) {
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
    private static HttpPost setHeadersToPost(HttpPost httpPost, Map<String, String> headers) {
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
    private static HttpPut setHeadersToPut(HttpPut httpPut, Map<String, String> headers) {
        if (headers != null && headers.size() != 0) {
            for (String key : headers.keySet()) {
                httpPut.addHeader(key, headers.get(key));
            }
        }
        return httpPut;
    }


    /**
     * 填充请求体参数至Post请求中
     * @param httpPost HttpPost
     * @param params 请求参数
     * @return HttpPost
     */
    private static HttpPost setParamsToRequest(HttpPost httpPost, Map<String, Object> params) {

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


    /**
     * 发送 GET 请求（HTTP），不带输入数据
     * @param url 请求地址
     * @return ResponseDataResult
     */
    public static ResponseDataResult doGet(String url) {
        return doGet(url, new HashMap<String, Object>(), new HashMap<String, String>());
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

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = null;
        HttpResponse response = null;
        ResponseDataResult responseDataResult = new ResponseDataResult();
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(params.get(key));
                i++;
            }
        }
        apiUrl += param;
        String result = null;
        try {
            HttpEntity entity = null;
            httpGet = new HttpGet(apiUrl);
            httpGet = setHeadersToGet(httpGet, headers);
            response = httpClient.execute(httpGet);
            if (response != null) {
                entity = response.getEntity();
            }

            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
                responseDataResult.setResult(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return responseDataResult;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     * @param url 请求参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPost(String url) {
        return doPost(url, new HashMap<String, Object>(), new HashMap<String, String>());
    }

    /**
     * 发送 POST 请求（HTTP），
     * 参数为JSON形式，无请求头参数
     * @param url 请求地址
     * @param json json对象
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPost(String url, Object json) {
        return doPost(url, json, null);
    }

    /**
     * 发送 POST 请求（HTTP）,
     * 参数为application/x-www-form-urlencoded的K-V形式,无请求头参数
     * @param url 请求地址
     * @param params 请求参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPost(String url, Map<String, Object> params) {
        return doPost(url, params, null);
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式 ，有请求头参数
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPost(String url, Map<String, Object> params, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        ResponseDataResult responseDataResult = new ResponseDataResult();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        try {
            httpPost.setConfig(requestConfig);
            httpPost = setHeadersToPost(httpPost, headers);
            httpPost = setParamsToRequest(httpPost, params);
            response = httpClient.execute(httpPost);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (statusCode != HttpStatus.SC_OK || entity == null) {
                    responseDataResult.setSuccess(false);
                    responseDataResult.setErrorCode(String.valueOf(statusCode));
                    responseDataResult.setErrorMessage(response.getStatusLine().getReasonPhrase());
                    return responseDataResult;
                }
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
            responseDataResult.setSuccess(true);
            responseDataResult.setResult(httpStr);
        } catch (IOException e) {
            responseDataResult.setSuccess(false);
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseDataResult;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式，有请求头参数
     * @param url 请求地址
     * @param json    json对象
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPost(String url, Object json, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        ResponseDataResult responseDataResult = new ResponseDataResult();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            httpPost = setHeadersToPost(httpPost, headers);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                HttpEntity entity = response.getEntity();
                if (statusCode != HttpStatus.SC_OK || entity == null) {
                    responseDataResult.setSuccess(false);
                    responseDataResult.setErrorCode(String.valueOf(statusCode));
                    responseDataResult.setErrorMessage(response.getStatusLine().getReasonPhrase());
                    return responseDataResult;
                }
                httpStr = EntityUtils.toString(entity, "UTF-8");
            }
            responseDataResult.setSuccess(true);
            responseDataResult.setResult(httpStr);
        } catch (IOException e) {
            responseDataResult.setSuccess(false);
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseDataResult;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），无K-V形式参数，无请求头参数
     * @param url API接口URL
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPostSSL(String url) {
        return doPostSSL(url, null, null);
    }

    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式，无请求头参数
     * @param url    API接口URL
     * @param params 参数map
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPostSSL(String url, Map<String, Object> params) {
        return doPostSSL(url, params, null);
    }

    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式，有请求头参数
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPostSSL(String url, Map<String, Object> params, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        ResponseDataResult responseDataResult = new ResponseDataResult();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            HttpEntity entity = null;
            httpPost.setConfig(requestConfig);

            httpPost = setHeadersToPost(httpPost, headers);
            httpPost = setParamsToRequest(httpPost, params);
            response = httpClient.execute(httpPost);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                entity = response.getEntity();
                if (statusCode != HttpStatus.SC_OK || entity == null) {
                    responseDataResult.setSuccess(false);
                    responseDataResult.setErrorCode(String.valueOf(statusCode));
                    responseDataResult.setErrorMessage(response.getStatusLine().getReasonPhrase());
                    return responseDataResult;
                }
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
            responseDataResult.setSuccess(true);
            responseDataResult.setResult(httpStr);
        } catch (Exception e) {
            responseDataResult.setSuccess(false);
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseDataResult;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式，有请求头参数
     * @param url  API接口URL
     * @param json JSON对象
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPostSSL(String url, Object json) {
        return doPostSSL(url, json, null);
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式，有请求头参数
     * @param url     API接口URL
     * @param json    JSON对象
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPostSSL(String url, Object json, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        ResponseDataResult responseDataResult = new ResponseDataResult();
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            HttpEntity entity = null;
            httpPost.setConfig(requestConfig);
            httpPost = setHeadersToPost(httpPost, headers);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                entity = response.getEntity();
                if (statusCode != HttpStatus.SC_OK || entity == null) {
                    return responseDataResult;
                }
            }

            httpStr = EntityUtils.toString(entity, "utf-8");
            responseDataResult.setSuccess(true);
            responseDataResult.setResult(httpStr);
        } catch (Exception e) {
            responseDataResult.setSuccess(false);
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseDataResult;
    }

    /**
     * 发送 SSL GET 请求（HTTPs），无参数，无请求头参数
     * @param url API接口URL
     * @return ResponseDataResult
     */
    public static ResponseDataResult doGetSSL(String url) {
        return doGetSSL(url, null, null);
    }

    /**
     * 发送 SSL GET 请求（HTTPs），K-V形式，无请求头参数
     * @param url    API接口URL
     * @param params 参数map
     * @return ResponseDataResult
     */
    public static ResponseDataResult doGetSSL(String url, Map<String, Object> params) {
        return doGetSSL(url, params, null);
    }

    /**
     * 发送 SSL GET 请求（HTTPs），K-V形式，有请求头参数
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doGetSSL(String url, Map<String, Object> params, Map<String, String> headers) {

        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(createSSLConnSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = null;
        HttpResponse response = null;
        ResponseDataResult responseDataResult = new ResponseDataResult();
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                if (i == 0)
                    param.append("?");
                else
                    param.append("&");
                param.append(key).append("=").append(params.get(key));
                i++;
            }
        }
        apiUrl += param;
        String result = null;
        try {
            HttpEntity entity = null;
            httpGet = new HttpGet(apiUrl);
            httpGet.setConfig(requestConfig);
            httpGet = setHeadersToGet(httpGet, headers);
            response = httpclient.execute(httpGet);
            if (response != null) {
                entity = response.getEntity();
            }

            if (entity != null) {
                InputStream instream = entity.getContent();
                result = IOUtils.toString(instream, "UTF-8");
                responseDataResult.setResult(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return responseDataResult;
    }


    /**
     * 发送 PUT 请求（HTTP），无参数，无请求头参数
     * @param url API接口URL
     * @return ResponseDataResult
     */
    public static ResponseDataResult put(String url) {
        return put(url, null, null);
    }

    /**
     * 发送 PUT 请求（HTTP），K-V形式，无请求头参数
     * @param url    API接口URL
     * @param params 参数map
     * @return ResponseDataResult
     */
    public static ResponseDataResult put(String url, Map<String, String> params) {
        return put(url, params, null);
    }

    /**
     * 发送 PUT 请求（HTTP），K-V形式，有请求头参数
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult put(String url, Map<String, String> params, Map<String, String> headers) {
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        //String responseText = "";
        HttpEntity entity = null;
        ResponseDataResult responseDataResult = new ResponseDataResult();
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            HttpPut httpPut = new HttpPut(url);
            if (headers != null) {
                Set<String> set = headers.keySet();
                for (String item : set) {
                    String value = headers.get(item);
                    httpPut.addHeader(item, value);
                }
            }
            if (params != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : params.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                httpPut.setEntity(new UrlEncodedFormEntity(paramList, CODE));
            }
            response = client.execute(httpPut);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                entity = response.getEntity();
                if (statusCode != HttpStatus.SC_OK || entity == null) {
                    return responseDataResult;
                }
            }

            httpStr = EntityUtils.toString(entity, "utf-8");
            responseDataResult.setResult(httpStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    EntityUtils.consume(response.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseDataResult;
    }


    /**
     * 发送 SSL PUT 请求（HTTP），JSON形式，无请求头参数
     * @param url  API接口URL
     * @param json JSON对象
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPut(String url, Object json) {
        return doPut(url, json, null);
    }

    /**
     * 发送 PUT 请求（HTTP），JSON形式，有请求头参数
     * @param url     API接口URL
     * @param json    JSON对象
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doPut(String url, Object json, Map<String, String> headers) {
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        ResponseDataResult responseDataResult = new ResponseDataResult();
        HttpPut httpPut = new HttpPut(url);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            HttpEntity entity = null;
            httpPut.setConfig(requestConfig);
            httpPut = setHeadersToPut(httpPut, headers);
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");//解决中文乱码问题
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPut.setEntity(stringEntity);
            response = httpClient.execute(httpPut);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                entity = response.getEntity();
                if (statusCode != HttpStatus.SC_OK || entity == null) {
                    return responseDataResult;
                }
            }

            httpStr = EntityUtils.toString(entity, "utf-8");
            responseDataResult.setResult(httpStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseDataResult;
    }

    /**
     * 发送 DELETE 请求（HTTP）,无参数
     * @param url API接口URL
     * @return ResponseDataResult
     */
    public static ResponseDataResult doDelete(String url) {
        return doDelete(url, null, null);
    }

    /**
     * 发送 DELETE 请求（HTTP），K-V形式，无请求头参数
     * @param url    API接口URL
     * @param params 参数map
     * @return ResponseDataResult
     */
    public static ResponseDataResult doDelete(String url, Map<String, String> params) {
        return doDelete(url, params, null);
    }

    /**
     * 发送 DELETE 请求（HTTP），K-V形式，有请求头参数
     * @param url     API接口URL
     * @param params  参数map
     * @param headers 请求头参数
     * @return ResponseDataResult
     */
    public static ResponseDataResult doDelete(String url, Map<String, String> params, Map<String, String> headers) {
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        //String responseText = "";
        HttpEntity entity = null;
        ResponseDataResult responseDataResult = new ResponseDataResult();
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            if (headers != null) {
                Set<String> set = headers.keySet();
                for (String item : set) {
                    String value = headers.get(item);
                    httpDelete.addHeader(item, value);
                }
            }
            if (params != null) {
                URIBuilder uriBuilder = new URIBuilder(url);
                if (params != null) {
                    for (String key : params.keySet()) {
                        uriBuilder.setParameter(key, params.get(key));
                    }
                }
                URI uri = uriBuilder.build();
                httpDelete.setURI(uri);
            }
            response = client.execute(httpDelete);
            if (response != null) {
                int statusCode = response.getStatusLine().getStatusCode();
                entity = response.getEntity();
                if (statusCode != HttpStatus.SC_OK || entity == null) {
                    responseDataResult.setSuccess(false);
                    responseDataResult.setErrorCode(String.valueOf(statusCode));
                    responseDataResult.setErrorMessage(response.getStatusLine().getReasonPhrase());
                    return responseDataResult;
                }
            }

            httpStr = EntityUtils.toString(entity, "utf-8");
            responseDataResult.setResult(httpStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    EntityUtils.consume(response.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseDataResult;
    }

    /**
     * 创建SSL安全连接
     * @return SSLConnectionSocketFactory
     */
    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {

                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }

                @Override
                public void verify(String host, SSLSocket ssl) throws IOException {
                }

                @Override
                public void verify(String host, X509Certificate cert) throws SSLException {
                }

                @Override
                public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                }
            });
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslsf;
    }

}

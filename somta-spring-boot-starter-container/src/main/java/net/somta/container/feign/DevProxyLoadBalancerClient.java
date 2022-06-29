package net.somta.container.feign;

import com.alibaba.cloud.nacos.NacosServiceInstance;
import feign.Client;
import feign.Request;
import feign.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class DevProxyLoadBalancerClient extends FeignBlockingLoadBalancerClient {

    private static final Log LOG = LogFactory.getLog(FeignBlockingLoadBalancerClient.class);

    private final Client delegate;

    private final LoadBalancerClient loadBalancerClient;

    private final LoadBalancerProperties properties;

    private final LoadBalancerClientFactory loadBalancerClientFactory;

    public DevProxyLoadBalancerClient(Client delegate, LoadBalancerClient loadBalancerClient, LoadBalancerProperties properties, LoadBalancerClientFactory loadBalancerClientFactory) {
        super(delegate, loadBalancerClient, properties, loadBalancerClientFactory);
        this.delegate = delegate;
        this.loadBalancerClient = loadBalancerClient;
        this.properties = properties;
        this.loadBalancerClientFactory = loadBalancerClientFactory;
    }

    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        final URI originalUri = URI.create(request.url());
        String serviceId = originalUri.getHost();
        Assert.state(serviceId != null, "Request URI does not contain a valid hostname: " + originalUri);
        String hint = getHint(serviceId);
        DefaultRequest<RequestDataContext> lbRequest = new DefaultRequest<>(new RequestDataContext(buildRequestData(request), hint));
        ServiceInstance instance = loadBalancerClient.choose(serviceId, lbRequest);
        if (instance == null) {
            String message = "Load balancer does not contain an instance for the service " + serviceId;
            if (LOG.isWarnEnabled()) {
                LOG.warn(message);
            }
            return Response.builder().request(request).status(HttpStatus.SERVICE_UNAVAILABLE.value())
                    .body(message, StandardCharsets.UTF_8).build();
        }
        //String reconstructedUrl = loadBalancerClient.reconstructURI(instance, originalUri).toString();
        //Request newRequest = buildRequest(request, reconstructedUrl);
        Request newRequest = buildCustomRequest(request,instance, originalUri);
        try {
            Response response = delegate.execute(newRequest, options);
            return response;
        }catch (Exception exception) {
            throw exception;
        }
    }



    private Request buildCustomRequest(Request request,ServiceInstance serviceInstance, URI original){
        Map<String, Collection<String>> newHeaders = new HashMap<>();
        //
        request.headers().forEach((key, value) -> newHeaders.put(key, new ArrayList<>(value)));
        //
        newHeaders.put("x-remote-service-name", Collections.singletonList(serviceInstance.getServiceId()));
        newHeaders.put("x-remote-service-host", Collections.singletonList(serviceInstance.getHost()));
        newHeaders.put("x-remote-service-port", Collections.singletonList(String.valueOf(serviceInstance.getPort())));

        NacosServiceInstance instance = (NacosServiceInstance)serviceInstance;
        instance.setHost("127.0.0.1");
        instance.setPort(8888);
        String host = instance.getHost();
        String reconstructedUrl = "http://"+host + ":" + instance.getPort() + original.getPath();

        return Request.create(request.httpMethod(), reconstructedUrl, newHeaders, request.body(),
                request.charset(), request.requestTemplate());
    }


    private RequestData buildRequestData(Request request) {
        HttpHeaders requestHeaders = new HttpHeaders();
        request.headers().forEach((key, value) -> requestHeaders.put(key, new ArrayList<>(value)));
        return new RequestData(HttpMethod.resolve(request.httpMethod().name()), URI.create(request.url()),
                requestHeaders, null, new HashMap<>());
    }

    private String getHint(String serviceId) {
        String defaultHint = properties.getHint().getOrDefault("default", "default");
        String hintPropertyValue = properties.getHint().get(serviceId);
        return hintPropertyValue != null ? hintPropertyValue : defaultHint;
    }
}

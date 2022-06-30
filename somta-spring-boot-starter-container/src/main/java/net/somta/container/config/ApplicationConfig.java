package net.somta.container.config;

import feign.Client;
import net.somta.container.feign.DevProxyLoadBalancerClient;
import net.somta.container.properties.DevProxyProperties;
import net.somta.container.utils.DevUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.cloud.openfeign.loadbalancer.OnRetryNotEnabledCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Autowired
    private DevProxyProperties devProxyProperties;

    @Bean
    @ConditionalOnMissingBean
    @Conditional(OnRetryNotEnabledCondition.class)
    public Client feignClient(LoadBalancerClient loadBalancerClient, LoadBalancerProperties properties,
                              LoadBalancerClientFactory loadBalancerClientFactory) {
        if(devProxyProperties.isEnable() && DevUtil.isInDevelopmentMode()){
            return new DevProxyLoadBalancerClient(new Client.Default(null, null), loadBalancerClient, properties,
                    loadBalancerClientFactory,devProxyProperties);
        } else {
            return new FeignBlockingLoadBalancerClient(new Client.Default(null, null), loadBalancerClient, properties,
                    loadBalancerClientFactory);
        }
    }

}

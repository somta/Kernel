package net.somta.demo.configuration;

import net.somta.cache.impl.CacheTemplateFactory;
import net.somta.cache.impl.ICacheTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 应用全局配置类
 * @Date: 2020/12/16
 * @Author: 明天的地平线
 * @Blog: https://somta.net/
 * @Version: 1.0.0
 */
@Configuration
public class ApplicationConfiguration {


   /* @Bean
    public ICacheTemplate getCacheBean(CacheProperties cacheProperties,RedisProperties redisProperties){
        ICacheTemplate cacheTemplate = CacheTemplateFactory.getCacheTemplate(cacheProperties,redisProperties);
        return cacheTemplate;
    }*/


}

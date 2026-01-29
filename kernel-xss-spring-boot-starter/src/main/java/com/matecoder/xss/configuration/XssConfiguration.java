package com.matecoder.xss.configuration;

import com.matecoder.xss.core.*;
import com.matecoder.xss.properties.XssProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * xss配置类
 * @author husong
 **/
@EnableConfigurationProperties(XssProperties.class)
@ConditionalOnProperty(
        prefix = XssProperties.PREFIX,
        name = "enabled",
        havingValue = "true",
        matchIfMissing = true
)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class XssConfiguration implements WebMvcConfigurer {

    private final XssProperties xssProperties;

    public XssConfiguration(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
    }


    @Bean
    @ConditionalOnMissingBean
    public IXssCleaner xssCleaner(XssProperties properties) {
        return new DefaultXssCleaner(properties);
    }

    @Bean
    public FormXssCleaner formXssCleaner(XssProperties properties,
                                         IXssCleaner xssCleaner) {
        return new FormXssCleaner(properties, xssCleaner);
    }


    /**
     * todo 这里加了自己的自定义转换后，可能会失效,要找一种优雅的方式，既能兼容xss又能支持自定义objectmapper的转换，还相互不强耦合
     * @param properties
     * @param xssCleaner
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer xssJacksonCustomizer(XssProperties properties,
                                                                      IXssCleaner xssCleaner) {
        return builder -> builder.deserializerByType(String.class, new JsonXssCleaner(properties, xssCleaner));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> patterns = xssProperties.getPathPatterns();
        if (patterns.isEmpty()) {
            patterns.add("/**");
        }
        XssCleanInterceptor interceptor = new XssCleanInterceptor(xssProperties);
        registry.addInterceptor(interceptor)
                .addPathPatterns(patterns)
                .excludePathPatterns(xssProperties.getPathExcludePatterns())
                .order(Ordered.LOWEST_PRECEDENCE);
    }

}

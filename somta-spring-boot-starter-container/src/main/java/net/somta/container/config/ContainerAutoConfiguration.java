package net.somta.container.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * starter的自动配置类
 */
@Configuration
@EnableConfigurationProperties
@ComponentScan(basePackages = {"net.somta.container"})
public class ContainerAutoConfiguration {

}

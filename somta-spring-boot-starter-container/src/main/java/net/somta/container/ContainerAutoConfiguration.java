package net.somta.container;

import net.somta.container.properties.ContainerProperties;
import net.somta.container.properties.DevProxyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * starter的自动配置类
 */
@Configuration
@EnableConfigurationProperties({ContainerProperties.class,DevProxyProperties.class})
@ComponentScan(basePackages = {"net.somta.container"})
public class ContainerAutoConfiguration {

}

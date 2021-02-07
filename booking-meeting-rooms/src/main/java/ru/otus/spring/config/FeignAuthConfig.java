package ru.otus.spring.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MTronina
 */
@Configuration
@EnableConfigurationProperties(IntegrationProperties.class)
public class FeignAuthConfig {

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor(IntegrationProperties prop) {
        return new BasicAuthRequestInterceptor(prop.getUsername(), prop.getPassword());
    }
}

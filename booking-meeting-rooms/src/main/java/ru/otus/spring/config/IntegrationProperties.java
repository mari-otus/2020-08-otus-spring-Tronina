package ru.otus.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MTronina
 */
@Data
@ConfigurationProperties("integration.auth")
public class IntegrationProperties {

    private String username;
    private String password;

}

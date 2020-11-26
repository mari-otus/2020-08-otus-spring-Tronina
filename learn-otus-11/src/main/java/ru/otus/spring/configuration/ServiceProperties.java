package ru.otus.spring.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author MTronina
 */
@Data
@ConfigurationProperties("server.servlet")
public class ServiceProperties {

    private String contextPath;
}

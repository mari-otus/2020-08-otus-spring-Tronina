package ru.otus.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * @author MTronina
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AclMethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

}

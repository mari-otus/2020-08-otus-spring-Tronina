package ru.otus.spring.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.client.NotificationClient;
import ru.otus.spring.client.UserClient;

/**
 * @author MTronina
 */
@EnableFeignClients(clients = {
        UserClient.class,
        NotificationClient.class
})
@Configuration
public class FeignClientConfig {
}
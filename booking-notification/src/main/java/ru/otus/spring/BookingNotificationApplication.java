package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author MTronina
 */
@EnableEurekaClient
@SpringBootApplication
public class BookingNotificationApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingNotificationApplication.class, args);
    }
}

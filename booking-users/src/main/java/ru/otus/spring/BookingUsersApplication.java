package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author MTronina
 */
@EnableEurekaClient
@SpringBootApplication
public class BookingUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookingUsersApplication.class, args);
    }
}

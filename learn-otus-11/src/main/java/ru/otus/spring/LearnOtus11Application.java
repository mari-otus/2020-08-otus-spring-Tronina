package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class LearnOtus11Application {

    public static void main(String[] args) {
        SpringApplication.run(LearnOtus11Application.class, args);
    }

}

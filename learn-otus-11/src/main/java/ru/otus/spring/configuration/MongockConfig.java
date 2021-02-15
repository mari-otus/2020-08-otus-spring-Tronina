package ru.otus.spring.configuration;

import com.github.cloudyrock.mongock.SpringMongock;
import com.github.cloudyrock.mongock.SpringMongockBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Конфигурация инструментария миграции БД для MongoDB.
 *
 * @author MTronina
 */
@Configuration
public class MongockConfig {

    @Bean
    public SpringMongock springMongock(MongoTemplate mongoTemplate, Environment springEnvironment) {
        return new SpringMongockBuilder(mongoTemplate, "ru.otus.spring.db")
                .setSpringEnvironment(springEnvironment)
                .build();
    }
}

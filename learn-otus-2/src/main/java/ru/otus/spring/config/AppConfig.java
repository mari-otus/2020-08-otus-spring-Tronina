package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.otus.spring.service.MessageService;
import ru.otus.spring.service.MessageServiceImpl;

/**
 * @author MTronina
 */
@PropertySource("classpath:application.properties")
@Configuration
public class AppConfig {

    @Bean
    public Resource resource(@Value("${csv.file}") String csvName) {
        return new ClassPathResource(csvName);
    }

    @Bean
    public MessageService printService() {
        return new MessageServiceImpl(System.in, System.out);
    }

}

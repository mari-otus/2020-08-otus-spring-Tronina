package ru.otus.spring.configuration;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.spring.handler.BookHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author MTronina
 */
@EnableConfigurationProperties(ServiceProperties.class)
@AllArgsConstructor
@Configuration
public class RouteConfig {

    private final BookHandler bookHandler;
    private final ServiceProperties serviceProperties;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route()
                .path(serviceProperties.getContextPath(), builder -> builder
                        .GET("/books", accept(MediaType.APPLICATION_JSON), bookHandler::getAllBook)
                        .DELETE("/books/{id}", accept(MediaType.APPLICATION_JSON), bookHandler::removeBook)
                        .GET("/books/{id}", accept(MediaType.APPLICATION_JSON), bookHandler::getBook)
                        .POST("/books", accept(MediaType.APPLICATION_JSON), bookHandler::saveBook)
                ).build();

    }
}

package ru.otus.spring.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.netflix.hystrix.HystrixCircuitBreaker;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import ru.otus.spring.abstracts.AbstractIntegrationTest;
import ru.otus.spring.client.UserClient;
import ru.otus.spring.dto.UserDto;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @author MTronina
 */
@EnableCircuitBreaker
@EnableAspectJAutoProxy
@DisplayName("UserClient должен")
class BookingControllerTest extends AbstractIntegrationTest {

    public static final String COMMAND_KEY_GET_USER_BY_LOGIN = "UserClient#getUserByLogin(String)";
    public static final String LOGIN_SUCCESS = "adminSuccess";
    public static final String LOGIN_FAILED = "adminFailed";

    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build();

    public static final float ERROR_THRESHOLD_PERCENTAGE = 50.0f;
    public static final int REQUEST_VOLUME_THRESHOLD = 20;
    public static final int ERROR_THRESHOLD_INT =
            (int) Math.round(ERROR_THRESHOLD_PERCENTAGE * REQUEST_VOLUME_THRESHOLD / 100.0);

    @Autowired
    private UserClient userClient;

    @SneakyThrows
    @DisplayName("при получении пользователя: CircuitBreaker -> OPEN, если частота ошибочных запросов >= errorThresholdPercentage " +
            "и успешно обрабатывать поступившие валидные запросы, пока общее кол-во запросов не достигнет requestVolumeThreshold")
    @Test
    void shouldOpenCircuitBreakerForGet() {
        mockServerUserClient();

        IntStream.rangeClosed(1, ERROR_THRESHOLD_INT).forEach(
                iter -> assertThrows(RuntimeException.class,
                        () -> userClient.getUserByLogin(LOGIN_FAILED))
        );

        IntStream.rangeClosed(1, REQUEST_VOLUME_THRESHOLD - ERROR_THRESHOLD_INT).forEach(
                iter -> userClient.getUserByLogin(LOGIN_SUCCESS));

        Thread.sleep(1000);

        assertThrows(HystrixRuntimeException.class,
                () -> userClient.getUserByLogin(LOGIN_SUCCESS));

        HystrixCircuitBreaker circuitBreaker = HystrixCircuitBreaker.Factory
                .getInstance(HystrixCommandKey.Factory.asKey(COMMAND_KEY_GET_USER_BY_LOGIN));
        assertTrue(circuitBreaker.isOpen());
        assertFalse(circuitBreaker.allowRequest());
    }

    @SneakyThrows
    @DisplayName("при получении пользователя: CircuitBreaker in CLOSED, если частота ошибочных запросов < errorThresholdPercentage " +
            "и успешно обрабатывать поступившие валидные запросы")
    @Test
    void shouldClosedCircuitBreakerForGet() {
        mockServerUserClient();

        IntStream.rangeClosed(1, ERROR_THRESHOLD_INT - 1).forEach(
                iter -> assertThrows(RuntimeException.class,
                        () -> userClient.getUserByLogin(LOGIN_FAILED))
        );

        IntStream.rangeClosed(1, REQUEST_VOLUME_THRESHOLD - (ERROR_THRESHOLD_INT - 1)).forEach(
                iter -> userClient.getUserByLogin(LOGIN_SUCCESS));

        assertDoesNotThrow(() -> userClient.getUserByLogin(LOGIN_SUCCESS));

        Thread.sleep(1000);

        HystrixCircuitBreaker circuitBreaker = HystrixCircuitBreaker.Factory
                .getInstance(HystrixCommandKey.Factory.asKey(COMMAND_KEY_GET_USER_BY_LOGIN));
        assertFalse(circuitBreaker.isOpen());
        assertTrue(circuitBreaker.allowRequest());
    }

    private void mockServerUserClient() throws JsonProcessingException {
        UserDto userDto = UserDto.builder()
                .fio("Петров")
                .build();
        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/booking_users/users/{login}")
                        .withPathParameter("login", LOGIN_SUCCESS)
                )
                .respond(response()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .withBody(mapper.writeValueAsString(userDto))
                );

        mockServerClient
                .when(request()
                        .withMethod("GET")
                        .withPath("/booking_users/users/{login}")
                        .withPathParameter("login", LOGIN_FAILED)
                )
                .respond(response()
                        .withStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                );
    }
}
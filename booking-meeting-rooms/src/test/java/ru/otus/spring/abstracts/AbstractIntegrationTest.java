package ru.otus.spring.abstracts;

import com.netflix.hystrix.Hystrix;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.client.MockServerClient;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author MTronina
 */
@SpringBootTest
@AutoConfigureMockMvc
@MockServerTest({"integration.service.users.url:http://localhost:${mockServerPort}/booking_users",
                 "integration.service.notification.url:http://localhost:${mockServerPort}/booking_notification"})
public abstract class AbstractIntegrationTest {

    protected MockServerClient mockServerClient;

    @Autowired
    protected MockMvc mvc;

    protected static final int TIMEOUT = 1000;

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("feign.client.config.account.connectTimeout", () -> TIMEOUT);
        registry.add("feign.client.config.account.readTimeout", () -> TIMEOUT);
    }

    @BeforeEach
    public void setUp() {
        Hystrix.reset();
    }

}

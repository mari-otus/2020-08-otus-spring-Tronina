package ru.otus.spring.abstracts;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.netflix.hystrix.Hystrix;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * @author MTronina
 */
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {AbstractIntegrationTest.LocalRibbonClientConfiguration.class})
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mvc;

    protected static final int TIMEOUT = 1000;

    @DynamicPropertySource
    public static void registerProperties(DynamicPropertyRegistry registry) {
        //registry.add("feign.client.config.account.connectTimeout", () -> TIMEOUT);
        //registry.add("feign.client.config.account.readTimeout", () -> TIMEOUT);
        registry.add("app.schedule.enabled", () -> false);
        registry.add("mockServerURL", mockServer::baseUrl);
    }

    @Rule
    public static final WireMockServer mockServer = new WireMockServer(wireMockConfig().dynamicPort());

    @BeforeAll
    static void init() {
        mockServer.start();
    }

    @AfterAll
    static void destroy() {
        mockServer.stop();
    }

    @AfterEach
    public void resetAll() {
        mockServer.resetAll();
    }

    @BeforeEach
    public void setUp() {
        Hystrix.reset();
    }

    @TestConfiguration
    public static class LocalRibbonClientConfiguration {
        @Bean
        public ServerList<Server> ribbonServerList() {
            return new StaticServerList<>(new Server("localhost", mockServer.port()));
        }
    }

}

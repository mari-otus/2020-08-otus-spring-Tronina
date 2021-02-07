package ru.otus.spring.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.PollableChannel;
import ru.otus.spring.service.TransformMessageService;


@Configuration
@IntegrationComponentScan
public class IntegrationConfig {
    private static final int DEFAULT_QUEUE_CAPACITY = 100;
    private static final int DEFAULT_POLLER_PERIOD = 1000;

    private static final String TRANSFORM_METHOD_NAME = "transform";

    @Autowired
    private TransformMessageService messageTransformer;

    @Autowired
    private JavaMailSender mailSender;

    @Bean
    public PollableChannel bookingRoomInChannel() {
        return MessageChannels.queue("bookingRoomInChannel", DEFAULT_QUEUE_CAPACITY).get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(DEFAULT_POLLER_PERIOD).get();
    }

    @Bean
    public IntegrationFlow appUserActivityFlow() {
        return f -> f.channel(bookingRoomInChannel())
                .transform(messageTransformer, TRANSFORM_METHOD_NAME)
                .handle(m -> {
                    SimpleMailMessage[] simpleMailMessages = (SimpleMailMessage[]) m.getPayload();
                    mailSender.send(simpleMailMessages);
                });
    }

}

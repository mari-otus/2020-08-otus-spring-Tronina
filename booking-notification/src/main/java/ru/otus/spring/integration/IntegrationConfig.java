package ru.otus.spring.integration;

import com.twilio.rest.api.v2010.account.MessageCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("transformMailMessageService")
    private TransformMessageService messageEmailTransformer;
    @Autowired
    @Qualifier("transformSmsMessageService")
    private TransformMessageService messageSmsTransformer;

    @Autowired
    private JavaMailSender mailSender;

    @Bean
    public PollableChannel bookingRoomInEmailChannel() {
        return MessageChannels.queue("bookingRoomInEmailChannel", DEFAULT_QUEUE_CAPACITY).get();
    }

    @Bean
    public PollableChannel bookingRoomInSmsChannel() {
        return MessageChannels.queue("bookingRoomInSmsChannel", DEFAULT_QUEUE_CAPACITY).get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(DEFAULT_POLLER_PERIOD).get();
    }

    @Bean
    public IntegrationFlow notifyEmailFlow() {
        return f -> f.channel(bookingRoomInEmailChannel())
                .transform(messageEmailTransformer, TRANSFORM_METHOD_NAME)
                .handle(m -> {
                    SimpleMailMessage[] simpleMailMessages = (SimpleMailMessage[]) m.getPayload();
                    if (simpleMailMessages.length > 0) {
                        mailSender.send(simpleMailMessages);
                    }
                });
    }

    @Bean
    public IntegrationFlow notifySmsFlow() {
        return f -> f.channel(bookingRoomInSmsChannel())
                .transform(messageSmsTransformer, TRANSFORM_METHOD_NAME)
                .split()
                .handle(message -> {
                    final MessageCreator payload = (MessageCreator) message.getPayload();
                    payload.create();
                });
    }

}

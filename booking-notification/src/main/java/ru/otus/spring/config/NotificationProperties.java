package ru.otus.spring.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("app")
public class NotificationProperties {

    private Email email;
    private Twilio sms;
    private Message message;

    @Data
    public static class Email {
        private String adminEmail;
    }

    @Data
    public static class Twilio {
        private String twilioAccountSid;
        private String twilioAuthToken;
        private String twilioPhoneNumber;
    }

    @Data
    public static class Message {
        private Integer capacity = 100;
        private Integer pollingTime = 1000;
    }
}

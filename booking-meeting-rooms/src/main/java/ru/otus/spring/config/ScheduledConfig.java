package ru.otus.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.otus.spring.listener.notification.NotificationManager;
import ru.otus.spring.service.BookingService;
import ru.otus.spring.sheduled.SchedulerCompleted;
import ru.otus.spring.sheduled.SchedulerNotification;

/**
 * @author MTronina
 */
@EnableScheduling
@Configuration
@ConditionalOnProperty(value = "app.schedule.enabled", havingValue = "true")
public class ScheduledConfig {

    @Bean
    @ConditionalOnProperty(name = "app.notify.enabled", havingValue = "true")
    public SchedulerNotification schedulerNotify(BookingService bookingService,
                                                 NotificationManager notificationManager) {
        return new SchedulerNotification(bookingService, notificationManager);
    }

    @Bean
    public SchedulerCompleted schedulerCompleted(BookingService bookingService) {
        return new SchedulerCompleted(bookingService);
    }
}

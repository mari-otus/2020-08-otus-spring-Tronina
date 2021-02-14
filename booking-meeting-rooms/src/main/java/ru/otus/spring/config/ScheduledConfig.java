/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2020 VTB Group. All rights reserved.
 */

package ru.otus.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.otus.spring.listener.notification.NotificationManager;
import ru.otus.spring.service.BookingService;
import ru.otus.spring.sheduled.SchedulerCompleted;
import ru.otus.spring.sheduled.SchedulerNotify;

/**
 * @author MTronina
 */
@EnableScheduling
@Configuration
@ConditionalOnProperty(value = "app.schedule.enabled", havingValue = "true")
public class ScheduledConfig {

    @Bean
    @ConditionalOnProperty(name = "app.notify.enabled", havingValue = "true")
    public SchedulerNotify schedulerNotify(BookingService bookingService,
                                           NotificationManager notificationManager) {
        return new SchedulerNotify(bookingService, notificationManager);
    }

    @Bean
    public SchedulerCompleted schedulerCompleted(BookingService bookingService) {
        return new SchedulerCompleted(bookingService);
    }
}

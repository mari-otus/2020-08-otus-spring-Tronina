package ru.otus.spring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.otus.spring.config.FeignAuthConfig;
import ru.otus.spring.dto.BookingNotify;
import ru.otus.spring.dto.BookingNotificationReminder;

import java.util.List;

/**
 * @author MTronina
 */
@FeignClient(name = "notification", url = "${integration.service.notification.url}",
        configuration = FeignAuthConfig.class)
public interface NotificationClient {

    @PostMapping("/notification/event")
    ResponseEntity<Void> notifyEvent(@RequestBody BookingNotify bookingNotify);

    @PostMapping("/notification/reminder")
    ResponseEntity<Void> notifyReminder(@RequestBody List<BookingNotificationReminder> bookingNotificationReminders);

}

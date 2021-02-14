package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.integration.BookingRoomGateway;
import ru.otus.spring.model.BookingNotificationReminder;
import ru.otus.spring.model.BookingNotify;

import java.util.List;

/**
 * @author MTronina
 */
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final BookingRoomGateway bookingRoomGateway;

    @PostMapping("/notification/event")
    public ResponseEntity<Void> notifyEvent(@RequestBody BookingNotify bookingNotify) {
        bookingRoomGateway.processEmailNotify(bookingNotify);
        bookingRoomGateway.processSmsNotify(bookingNotify);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notification/reminder")
    public ResponseEntity<Void> notifyReminder(@RequestBody List<BookingNotificationReminder> bookingNotificationReminders) {
        bookingRoomGateway.processEmailNotifyReminder(bookingNotificationReminders);
        bookingRoomGateway.processSmsNotifyReminder(bookingNotificationReminders);
        return ResponseEntity.ok().build();
    }
}

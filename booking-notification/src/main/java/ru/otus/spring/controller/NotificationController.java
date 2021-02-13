package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.integration.BookingRoomGateway;
import ru.otus.spring.model.BookingNotify;

/**
 * @author MTronina
 */
@RestController
@RequiredArgsConstructor
public class NotificationController {

    private final BookingRoomGateway bookingRoomGateway;

    @PostMapping("/notify")
    public ResponseEntity<Void> notify(@RequestBody BookingNotify bookingNotify) {
        bookingRoomGateway.processEmailNotify(bookingNotify);
        bookingRoomGateway.processSmsNotify(bookingNotify);
        return ResponseEntity.ok().build();
    }
}

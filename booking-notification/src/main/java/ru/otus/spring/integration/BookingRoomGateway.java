package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;
import ru.otus.spring.model.BookingNotify;

@MessagingGateway
public interface BookingRoomGateway {

    @Gateway(requestChannel = "bookingRoomInChannel")
    void processNotify(@Payload BookingNotify booking);
}

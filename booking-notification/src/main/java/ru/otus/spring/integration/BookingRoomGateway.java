package ru.otus.spring.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Payload;
import ru.otus.spring.model.BookingNotify;

@MessagingGateway
public interface BookingRoomGateway {

    @Gateway(requestChannel = "bookingRoomInEmailChannel")
    void processEmailNotify(@Payload BookingNotify booking);

    @Gateway(requestChannel = "bookingRoomInSmsChannel")
    void processSmsNotify(@Payload BookingNotify booking);
}

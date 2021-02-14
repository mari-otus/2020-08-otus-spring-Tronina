package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingNotificationEvent {

    private List<SubscriberDto> subscribers;

    private String roomName;

    private String fioOfBooking;

    private LocalDateTime beginBookingDate;

    private LocalDateTime endBookingDate;

    private LocalDateTime createBookingDate;

    private LocalDateTime updateBookingDate;

    private LocalDateTime deleteBookingDate;
}

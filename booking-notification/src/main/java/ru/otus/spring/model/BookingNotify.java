package ru.otus.spring.model;

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
public class BookingNotify {

    private List<Subscriber> subscribers;

    private String roomName;

    private String fioOfBooking;

    private LocalDateTime beginBookingDate;

    private LocalDateTime endBookingDate;

    private LocalDateTime createBookingDate;

    private LocalDateTime updateBookingDate;

    private LocalDateTime deleteBookingDate;

}

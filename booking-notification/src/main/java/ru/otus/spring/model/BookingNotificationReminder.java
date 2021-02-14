package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingNotificationReminder {

    private Subscriber subscriber;

    private String roomName;

    private LocalDateTime beginBookingDate;

    private LocalDateTime endBookingDate;
}

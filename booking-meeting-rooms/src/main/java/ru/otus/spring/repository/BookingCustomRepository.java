package ru.otus.spring.repository;

import ru.otus.spring.domain.Booking;
import ru.otus.spring.dto.BookingFilter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MTronina
 */
public interface BookingCustomRepository {

    List<Booking> findAllActiveByFilter(BookingFilter filter);

    List<Booking> findAllExistsActiveByFilter(Long id, String roomName, LocalDateTime beginDate, LocalDateTime endDate);

    void updateCompleteBookings();

}

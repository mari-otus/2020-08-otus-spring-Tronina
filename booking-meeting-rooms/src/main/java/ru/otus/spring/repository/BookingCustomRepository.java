package ru.otus.spring.repository;

import ru.otus.spring.domain.Booking;
import ru.otus.spring.dto.BookingFilter;

import java.util.List;

/**
 * @author MTronina
 */
public interface BookingCustomRepository {

    List<Booking> findAllActiveByFilter(BookingFilter filter);

    void updateCompleteBookings();
}

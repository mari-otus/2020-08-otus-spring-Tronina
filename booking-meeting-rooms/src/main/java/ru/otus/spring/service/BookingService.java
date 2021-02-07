package ru.otus.spring.service;

import ru.otus.spring.dto.BookingDto;
import ru.otus.spring.dto.BookingFilter;
import ru.otus.spring.security.AuthUserDetails;

import java.util.List;

/**
 * @author MTronina
 */
public interface BookingService {

    void createBooking(BookingDto bookingRequest, AuthUserDetails authUserDetails);

    void updateBooking(Long bookingId, BookingDto bookingRequest, AuthUserDetails authUserDetails);

    void deleteBooking(Long bookingId, AuthUserDetails authUserDetails);

    List<BookingDto> getBookings(BookingFilter bookingFilter);
}

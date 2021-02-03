package ru.otus.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.spring.dto.BookingDto;
import ru.otus.spring.dto.BookingFilter;
import ru.otus.spring.security.AuthUserDetails;

/**
 * @author MTronina
 */
public interface BookingService {

    void createBooking(BookingDto bookingRequest, AuthUserDetails authUserDetails);

    void updateBooking(Long bookingId, BookingDto bookingRequest);

    void deleteBooking(Long bookingId);

    Page<BookingDto> getBookings(BookingFilter bookingFilter, Pageable pageable);
}

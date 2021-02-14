package ru.otus.spring.service;

import ru.otus.spring.dto.BookingRequestDto;
import ru.otus.spring.dto.BookingResponseDto;
import ru.otus.spring.dto.BookingFilter;
import ru.otus.spring.security.AuthUserDetails;

import java.util.List;

/**
 * @author MTronina
 */
public interface BookingService {

    void createBooking(BookingRequestDto bookingRequest, AuthUserDetails authUserDetails);

    void updateBooking(Long bookingId, BookingRequestDto bookingRequest, AuthUserDetails authUserDetails);

    List<BookingResponseDto> deleteBooking(Long bookingId, AuthUserDetails authUserDetails);

    List<BookingResponseDto> getBookings(BookingFilter bookingFilter);

    BookingResponseDto getBooking(Long bookingId);

    void completedBookings();

    List<BookingResponseDto> getSoonStartingBookings(long minutes);
}

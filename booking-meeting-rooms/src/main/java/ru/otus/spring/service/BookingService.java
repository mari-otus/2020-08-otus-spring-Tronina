package ru.otus.spring.service;

import ru.otus.spring.dto.BookingFilter;
import ru.otus.spring.dto.BookingRequestDto;
import ru.otus.spring.dto.BookingResponseDto;
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

    /**
     * Переводит вес завершенные брони в статус удаленных.
     */
    void completedBookings();

    /**
     * Возвращает список броней, которые должны начаться в ближайшие минуты.
     *
     * @param minutes кол-во ближайших минут, за которые ищется начало брони.
     * @return список броней.
     */
    List<BookingResponseDto> getSoonStartingBookings(long minutes);
}

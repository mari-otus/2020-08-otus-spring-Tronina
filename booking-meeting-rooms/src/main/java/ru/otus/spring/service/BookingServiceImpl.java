package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.domain.Room;
import ru.otus.spring.dto.BookingDto;
import ru.otus.spring.dto.BookingFilter;
import ru.otus.spring.exception.ApplicationException;
import ru.otus.spring.mapper.BookingMapper;
import ru.otus.spring.repository.BookingRepository;
import ru.otus.spring.repository.RoomRepository;
import ru.otus.spring.security.AuthUserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper mapper;

    @Override
    public void createBooking(BookingDto bookingRequest, AuthUserDetails authUserDetails) {
        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(ApplicationException::new);

        Booking booking = Booking.builder()
                .room(room)
                .login(authUserDetails.getUsername())
                .beginDate(bookingRequest.getBeginDate())
                .endDate(bookingRequest.getEndDate())
                .createDate(LocalDateTime.now())
                .build();
        bookingRepository.save(booking);

    }

    @Override
    public void updateBooking(Long bookingId, BookingDto bookingRequest,
                              AuthUserDetails authUserDetails) {
        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(ApplicationException::new);

        Booking booking = bookingRepository.findByIdEqualsAndAndLoginEquals(bookingId,
                authUserDetails.getUsername())
                .orElseThrow(ApplicationException::new);
        booking.setRoom(room);
        booking.setBeginDate(bookingRequest.getBeginDate());
        booking.setEndDate(bookingRequest.getEndDate());
        booking.setUpdateDate(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long bookingId, AuthUserDetails authUserDetails) {
        Booking booking = bookingRepository.findByIdEqualsAndAndLoginEquals(bookingId,
                authUserDetails.getUsername())
                .orElseThrow(ApplicationException::new);
        booking.setDeleteDate(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Page<BookingDto> getBookings(BookingFilter bookingFilter, Pageable pageable) {
        return bookingRepository.findAllByFilter(bookingFilter, pageable)
                .map(mapper::toBookingDto);
    }

    @PostFilter("filterObject.login == authentication.name")
    @Override
    public List<BookingDto> getBookings(BookingFilter bookingFilter) {
        return bookingRepository.findAllByFilter(bookingFilter).stream()
                .map(mapper::toBookingDto)
                .collect(Collectors.toList());
    }

}

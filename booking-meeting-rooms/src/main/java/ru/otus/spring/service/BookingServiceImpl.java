package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.domain.Room;
import ru.otus.spring.domain.Status;
import ru.otus.spring.domain.User;
import ru.otus.spring.dto.BookingDto;
import ru.otus.spring.dto.BookingFilter;
import ru.otus.spring.exception.ApplicationException;
import ru.otus.spring.mapper.BookingMapper;
import ru.otus.spring.repository.room.BookingRepository;
import ru.otus.spring.repository.room.RoomRepository;
import ru.otus.spring.repository.user.UserRepository;
import ru.otus.spring.security.AuthUserDetails;

import java.time.LocalDateTime;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BookingMapper mapper;

    @Override
    public void createBooking(BookingDto bookingRequest, AuthUserDetails authUserDetails) {
        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(ApplicationException::new);

        User user = userRepository.findByLogin(authUserDetails.getUsername())
                .orElseThrow(ApplicationException::new);

        Booking booking = Booking.builder()
                .room(room)
                .user(user)
                .status(Status.ACTIVE)
                .beginDate(bookingRequest.getBeginDate())
                .endDate(bookingRequest.getEndDate())
                .createDate(LocalDateTime.now())
                .build();
        bookingRepository.save(booking);

    }

    @Override
    public void updateBooking(Long bookingId, BookingDto bookingRequest) {
        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(ApplicationException::new);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(ApplicationException::new);
        booking.setRoom(room);
        booking.setBeginDate(bookingRequest.getBeginDate());
        booking.setEndDate(bookingRequest.getEndDate());
        booking.setUpdateDate(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(ApplicationException::new);
        booking.setStatus(Status.DELETED);
        booking.setDeleteDate(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    @Override
    public Page<BookingDto> getBookings(BookingFilter bookingFilter, Pageable pageable) {
        return bookingRepository.findAllByFilter(bookingFilter, pageable)
                .map(mapper::toBookingDto);
    }

}

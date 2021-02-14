package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.domain.Room;
import ru.otus.spring.dto.BookingFilter;
import ru.otus.spring.dto.BookingRequestDto;
import ru.otus.spring.dto.BookingResponseDto;
import ru.otus.spring.exception.ApplicationException;
import ru.otus.spring.mapper.BookingMapper;
import ru.otus.spring.repository.BookingRepository;
import ru.otus.spring.repository.RoomRepository;
import ru.otus.spring.security.AuthUserDetails;

import java.text.MessageFormat;
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

    @Transactional
    @Override
    public void createBooking(BookingRequestDto bookingRequest, AuthUserDetails authUserDetails) {
        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(ApplicationException::new);

        checkExistsBooking(null, room.getRoomName(), bookingRequest.getBeginDate(), bookingRequest.getEndDate());

        Booking booking = Booking.builder()
                .room(room)
                .login(authUserDetails.getUsername())
                .beginDate(bookingRequest.getBeginDate())
                .endDate(bookingRequest.getEndDate())
                .createDate(LocalDateTime.now())
                .build();
        bookingRepository.save(booking);

    }

    @Transactional
    @Override
    public void updateBooking(Long bookingId, BookingRequestDto bookingRequest,
                              AuthUserDetails authUserDetails) {
        Booking booking = bookingRepository.findByIdEqualsAndAndLoginEquals(bookingId,
                authUserDetails.getUsername())
                .orElseThrow(() -> new AccessDeniedException("Доступ запрещен!"));

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(ApplicationException::new);

        checkExistsBooking(bookingId, room.getRoomName(), bookingRequest.getBeginDate(), bookingRequest.getEndDate());

        booking.setRoom(room);
        booking.setBeginDate(bookingRequest.getBeginDate());
        booking.setEndDate(bookingRequest.getEndDate());
        booking.setUpdateDate(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    private void checkExistsBooking(Long id, String roomName, LocalDateTime beginDate, LocalDateTime endDate) {
        List<Booking> existBookings = bookingRepository.findAllExistsActiveByFilter(
                id, roomName, beginDate, endDate
        );
        if (!existBookings.isEmpty()) {
            throw new ApplicationException(MessageFormat.format(
                    "Указанный период охватывает другие активные брони для комнаты \"{0}\"",
                    roomName));
        }
    }

    @Transactional
    @Override
    public List<BookingResponseDto> deleteBooking(Long bookingId, AuthUserDetails authUserDetails) {
        Booking booking = bookingRepository.findByIdEqualsAndAndLoginEquals(bookingId,
                authUserDetails.getUsername())
                .orElseThrow(() -> new AccessDeniedException("Доступ запрещен!"));
        booking.setDeleteDate(LocalDateTime.now());
        bookingRepository.save(booking);
        return getBookings(BookingFilter.builder().build());
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingResponseDto> getBookings(BookingFilter bookingFilter) {
        return bookingRepository.findAllActiveByFilter(bookingFilter).stream()
                .map(mapper::toBookingDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public BookingResponseDto getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .map(mapper::toBookingDto)
                .orElseThrow(ApplicationException::new);
    }

    @Transactional
    @Override
    public void completedBookings() {
        bookingRepository.updateCompleteBookings();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingResponseDto> getSoonStartingBookings(long minutes) {
        return bookingRepository.findAllByDeleteDateIsNullAndBeginDateBetween(LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(minutes))
                .stream()
                .map(mapper::toBookingDto)
                .collect(Collectors.toList());
    }

}

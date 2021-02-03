package ru.otus.spring.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.BookingDto;
import ru.otus.spring.dto.BookingFilter;
import ru.otus.spring.security.AuthUserDetails;
import ru.otus.spring.service.BookingService;

import java.security.Principal;

/**
 * @author MTronina
 */
@Api(tags = "Сервис бронирования переговорок")
@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<Void> createBookingRoom(
            @RequestBody BookingDto booking, Principal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthUserDetails userDetails = (AuthUserDetails) authenticationToken.getPrincipal();
        bookingService.createBooking(booking, userDetails);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> editBookingRoom(
            @PathVariable Long bookingId,
            @RequestBody BookingDto booking) {
        bookingService.updateBooking(bookingId, booking);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> deleteBookingRoom(
            @PathVariable Long bookingId) {
        bookingService.deleteBooking(bookingId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bookings/search")
    public ResponseEntity<Page<BookingDto>> getBookings(
            @RequestBody BookingFilter bookingFilter, Pageable pageable) {
        Page<BookingDto> bookings = bookingService.getBookings(bookingFilter, pageable);
        return ResponseEntity.ok(bookings);
    }
}

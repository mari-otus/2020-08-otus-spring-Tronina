package ru.otus.spring.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.BookingRequestDto;
import ru.otus.spring.dto.BookingResponseDto;
import ru.otus.spring.dto.BookingFilter;
import ru.otus.spring.security.AuthUserDetails;
import ru.otus.spring.service.BookingService;

import java.security.Principal;
import java.util.List;

/**
 * @author MTronina
 */
@Api(tags = "Сервис бронирования переговорок")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<Void> createBookingRoom(
            @RequestBody BookingRequestDto booking, Principal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthUserDetails userDetails = (AuthUserDetails) authenticationToken.getPrincipal();
        bookingService.createBooking(booking, userDetails);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> getBookingRoom(@PathVariable Long bookingId) {
        bookingService.getBooking(bookingId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/bookings/{bookingId}")
    public ResponseEntity<Void> editBookingRoom(
            @PathVariable Long bookingId,
            @RequestBody BookingRequestDto booking,
            Principal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthUserDetails userDetails = (AuthUserDetails) authenticationToken.getPrincipal();
        bookingService.updateBooking(bookingId, booking, userDetails);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/bookings/{bookingId}")
    public ResponseEntity<List<BookingResponseDto>> deleteBookingRoom(
            @PathVariable Long bookingId,
            Principal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthUserDetails userDetails = (AuthUserDetails) authenticationToken.getPrincipal();
        List<BookingResponseDto> bookings = bookingService.deleteBooking(bookingId, userDetails);
        return ResponseEntity.ok(bookings);
    }

    @PostMapping("/bookings/search")
    public ResponseEntity<List<BookingResponseDto>> getBookings(
            @RequestBody BookingFilter bookingFilter) {
        List<BookingResponseDto> bookings = bookingService.getBookings(bookingFilter);
        return ResponseEntity.ok(bookings);
    }
}

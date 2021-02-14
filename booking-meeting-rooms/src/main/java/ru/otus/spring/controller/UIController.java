package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.otus.spring.dto.BookingRequestDto;
import ru.otus.spring.dto.RoomFilter;
import ru.otus.spring.dto.RoomRequestDto;
import ru.otus.spring.service.BookingService;
import ru.otus.spring.service.RoomService;

/**
 * Контроллер для работы с UI.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@Controller
public class UIController {

    private final BookingService bookingService;
    private final RoomService roomService;

    /**
     * Переход на главную страницу.
     *
     * @return view name.
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/bookings/{id}")
    public String editBook(@PathVariable("id") long id, Model model) {
        model.addAttribute("booking", bookingService.getBooking(id));
        model.addAttribute("rooms", roomService.getRooms(RoomFilter.builder().build()));
        return "editBooking";
    }

    @GetMapping("/bookings/add")
    public String addBook(Model model) {
        model.addAttribute("booking", new BookingRequestDto());
        model.addAttribute("rooms", roomService.getRooms(RoomFilter.builder().build()));
        return "editBooking";
    }

    @GetMapping("/bookings/add/{roomId}")
    public String addBook(@PathVariable("roomId") long roomId, Model model) {
        model.addAttribute("booking", BookingRequestDto.builder()
                .roomId(roomId)
                .build());
        model.addAttribute("rooms", roomService.getRooms(RoomFilter.builder().build()));
        return "editBooking";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/rooms/{id}")
    public String editRoom(@PathVariable("id") long id,
                           Model model) {
        model.addAttribute("room", roomService.getRoom(id));
        return "editRoom";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/rooms/add")
    public String addRoom(Model model) {
        model.addAttribute("room", new RoomRequestDto());
        return "editRoom";
    }

    @GetMapping("/bookings")
    public String getBookings() {
        return "bookings";
    }

    @GetMapping("/rooms")
    public String getRooms() {
        return "rooms";
    }

    @GetMapping("/profiles")
    public String getProfile() {
        return "profile";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

}

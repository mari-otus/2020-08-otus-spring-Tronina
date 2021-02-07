package ru.otus.spring.listener.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.client.NotificationClient;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.domain.Profile;
import ru.otus.spring.domain.Subscribing;
import ru.otus.spring.dto.BookingNotify;
import ru.otus.spring.dto.ProfileUserDto;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.repository.ProfileRepository;
import ru.otus.spring.repository.SubscribingRepository;
import ru.otus.spring.security.AuthUserDetails;
import ru.otus.spring.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MTronina
 */
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.notify.enabled", havingValue = "true")
public class NotifyManager {

    private final ProfileRepository profileRepository;
    private final SubscribingRepository subscribingRepository;
    private final NotificationClient notificationClient;
    private final UserService userService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void notify(Booking booking) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        AuthUserDetails userDetails = (AuthUserDetails) authenticationToken.getPrincipal();
        final UserDto userEditor = userService.getUserByLogin(userDetails.getUsername());

        final Long roomId = booking.getRoom().getId();
        List<Subscribing> subscribings = subscribingRepository.findAllByRoom_Id(roomId);
        List<ProfileUserDto> profileUserDtos = subscribings.stream()
                .filter(subscribing -> !subscribing.getLogin().equals(userEditor.getLogin()))
                .map(subscribing -> {
                    Profile profile = profileRepository.findByLoginEquals(subscribing.getLogin())
                            .orElse(Profile.builder().build());
                    final UserDto userByLogin = userService.getUserByLogin(subscribing.getLogin());
                    return ProfileUserDto.builder()
                            .fio(userByLogin.getFio())
                            .email(profile.getEmail())
                            .mobilePhone(profile.getMobilePhone())
                            .isEmailNotify(profile.isEmailNotify())
                            .isPhoneNotify(profile.isPhoneNotify())
                            .build();
                })
                .collect(Collectors.toList());
        if (!profileUserDtos.isEmpty()) {
            BookingNotify bookingNotify = BookingNotify.builder()
                    .subscribers(profileUserDtos)
                    .roomName(booking.getRoom().getRoomName())
                    .fioOfBooking(userEditor.getFio())
                    .beginBookingDate(booking.getBeginDate())
                    .endBookingDate(booking.getEndDate())
                    .createBookingDate(booking.getCreateDate())
                    .updateBookingDate(booking.getUpdateDate())
                    .deleteBookingDate(booking.getDeleteDate())
                    .build();
            notificationClient.notify(bookingNotify);
        }
    }
}

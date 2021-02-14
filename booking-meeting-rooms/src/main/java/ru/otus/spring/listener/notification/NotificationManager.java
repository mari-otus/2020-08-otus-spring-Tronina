package ru.otus.spring.listener.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.otus.spring.dto.BookingResponseDto;
import ru.otus.spring.dto.BookingNotificationReminder;
import ru.otus.spring.dto.BookingNotificationEvent;
import ru.otus.spring.dto.SubscriberDto;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.repository.ProfileRepository;
import ru.otus.spring.repository.SubscribingRepository;
import ru.otus.spring.security.AuthUserDetails;
import ru.otus.spring.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MTronina
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.notify.enabled", havingValue = "true")
public class NotificationManager {

    private final ProfileRepository profileRepository;
    private final SubscribingRepository subscribingRepository;
    private final NotificationClient notificationClient;
    private final UserService userService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void notifyEvent(Booking booking) {
        try {
            Principal principal = SecurityContextHolder.getContext().getAuthentication();
            if (principal == null) {
                return;
            }
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
            AuthUserDetails userDetails = (AuthUserDetails) authenticationToken.getPrincipal();
            UserDto userEditor = userService.getUserByLogin(userDetails.getUsername());

            final Long roomId = booking.getRoom().getId();
            List<Subscribing> subscribers = subscribingRepository.findAllByRoom_Id(roomId);
            List<SubscriberDto> subscriberDtos = subscribers.stream()
                    .filter(subscribing -> !subscribing.getLogin().equals(userEditor.getLogin()))
                    .map(subscribing -> {
                        Profile profile = profileRepository.findByLoginEquals(subscribing.getLogin())
                                .orElse(Profile.builder().build());
                        final UserDto userByLogin = userService.getUserByLogin(subscribing.getLogin());
                        return SubscriberDto.builder()
                                .fio(userByLogin.getFio())
                                .email(profile.getEmail())
                                .mobilePhone(profile.getMobilePhone())
                                .isEmailNotify(profile.isEmailNotify())
                                .isPhoneNotify(profile.isPhoneNotify())
                                .build();
                    })
                    .collect(Collectors.toList());
            if (!subscriberDtos.isEmpty()) {
                BookingNotificationEvent bookingNotificationEvent = BookingNotificationEvent.builder()
                        .subscribers(subscriberDtos)
                        .roomName(booking.getRoom().getRoomName())
                        .fioOfBooking(userEditor.getFio())
                        .beginBookingDate(booking.getBeginDate())
                        .endBookingDate(booking.getEndDate())
                        .createBookingDate(booking.getCreateDate())
                        .updateBookingDate(booking.getUpdateDate())
                        .deleteBookingDate(booking.getDeleteDate())
                        .build();
                notificationClient.notifyEvent(bookingNotificationEvent);
            }
        } catch (Exception e) {
           log.error(e.getMessage(), e);
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
    public void notifyReminder(List<BookingResponseDto> bookings) {
        try {
            List<BookingNotificationReminder> bookingNotificationReminders = new ArrayList<>();
            bookings.stream()
                    .forEach(booking -> {
                        Optional<Profile> profileBookingUser = profileRepository.findByLoginEquals(booking.getLogin());
                        profileBookingUser.ifPresent(profile -> {
                            if (profile.isEmailNotify() || profile.isPhoneNotify()) {
                                final UserDto userByLogin = userService.getUserByLogin(profile.getLogin());
                                BookingNotificationReminder bookingNotificationReminder = BookingNotificationReminder
                                        .builder()
                                        .subscriber(SubscriberDto.builder()
                                                .fio(userByLogin.getFio())
                                                .email(profile.getEmail())
                                                .mobilePhone(profile.getMobilePhone())
                                                .isEmailNotify(profile.isEmailNotify())
                                                .isPhoneNotify(profile.isPhoneNotify())
                                                .build())
                                        .roomName(booking.getRoomName())
                                        .beginBookingDate(booking.getBeginDate())
                                        .endBookingDate(booking.getEndDate())
                                        .build();
                                bookingNotificationReminders.add(bookingNotificationReminder);
                            }
                        });
                    });
            notificationClient.notifyReminder(bookingNotificationReminders);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

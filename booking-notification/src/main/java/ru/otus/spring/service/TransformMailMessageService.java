package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.NotificationProperties;
import ru.otus.spring.model.BookingNotificationReminder;
import ru.otus.spring.model.BookingNotify;
import ru.otus.spring.model.Subscriber;

import java.text.MessageFormat;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class TransformMailMessageService implements TransformMessageService<SimpleMailMessage> {

    private final NotificationProperties notificationProperties;

    @Override
    public SimpleMailMessage[] transform(BookingNotify bookingNotify) {
        return bookingNotify.getSubscribers().stream()
                .filter(Subscriber::isEmailNotify)
                .map(profileUserDto -> {
                    final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                    simpleMailMessage.setTo(profileUserDto.getEmail());
                    simpleMailMessage.setFrom(notificationProperties.getEmail().getAdminEmail());
                    if (bookingNotify.getDeleteBookingDate() != null) {
                        simpleMailMessage.setSubject(MessageFormat.format("Уведомление. Удалена бронь на переговорку \"{0}\"", bookingNotify.getRoomName()));
                        simpleMailMessage.setText(MessageUtils.textDelete(bookingNotify, profileUserDto));
                    } else if (bookingNotify.getUpdateBookingDate() != null) {
                        simpleMailMessage.setSubject(MessageFormat.format("Уведомление. Изменена бронь на переговорку \"{0}\"", bookingNotify.getRoomName()));
                        simpleMailMessage.setText(MessageUtils.textUpdate(bookingNotify, profileUserDto));
                    } else {
                        simpleMailMessage.setSubject(MessageFormat.format("Уведомление. Создана бронь на переговорку \"{0}\"", bookingNotify.getRoomName()));
                        simpleMailMessage.setText(MessageUtils.textCreate(bookingNotify, profileUserDto));
                    }
                    return simpleMailMessage;
                })
                .toArray(SimpleMailMessage[]::new);
    }

    @Override
    public SimpleMailMessage transformReminder(BookingNotificationReminder message) {
        final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(message.getSubscriber().getEmail());
        simpleMailMessage.setFrom(notificationProperties.getEmail().getAdminEmail());
        simpleMailMessage.setSubject(MessageFormat.format("Уведомление. Скоро начнется Ваша бронь на переговорку \"{0}\"", message.getRoomName()));
        simpleMailMessage.setText(MessageUtils.textReminder(message));
        return simpleMailMessage;
    }

}

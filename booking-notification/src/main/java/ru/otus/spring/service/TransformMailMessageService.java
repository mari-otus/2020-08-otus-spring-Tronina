package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.NotificationProperties;
import ru.otus.spring.model.BookingNotify;
import ru.otus.spring.model.Subscriber;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

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
                    simpleMailMessage.setFrom(notificationProperties.getAdminEmail());
                    if (bookingNotify.getDeleteBookingDate() != null) {
                        simpleMailMessage.setSubject(MessageFormat.format("Уведомление. Удалена бронь на переговорку \"{0}\"", bookingNotify.getRoomName()));
                        simpleMailMessage.setText(textDelete(bookingNotify, profileUserDto));
                    } else if (bookingNotify.getUpdateBookingDate() != null) {
                        simpleMailMessage.setSubject(MessageFormat.format("Уведомление. Изменена бронь на переговорку \"{0}\"", bookingNotify.getRoomName()));
                        simpleMailMessage.setText(textUpdate(bookingNotify, profileUserDto));
                    } else {
                        simpleMailMessage.setSubject(MessageFormat.format("Уведомление. Создана бронь на переговорку \"{0}\"", bookingNotify.getRoomName()));
                        simpleMailMessage.setText(textCreate(bookingNotify, profileUserDto));
                    }
                    return simpleMailMessage;
                })
                .toArray(SimpleMailMessage[]::new);
    }

    private String textCreate(BookingNotify message, Subscriber subscriber) {
        return MessageFormat
                .format("{0}, {1} была забронирована переговорка \"{2}\" на период с {3} по {4}. \r\n" +
                                "Автор брони {5}.",
                        subscriber.getFio(),
                        message.getCreateBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.forLanguageTag("ru"))),
                        message.getRoomName(),
                        message.getBeginBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.forLanguageTag("ru"))),
                        message.getEndBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.forLanguageTag("ru"))),
                        message.getFioOfBooking()
                );
    }

    private String textUpdate(BookingNotify message, Subscriber subscriber) {
        return MessageFormat
                .format("{0}, {1} была изменена бронь переговорки \"{2}\". \r\n" +
                                "Период брони с {3} по {4}. \r\n" +
                                "Автор изменений {5}.",
                        subscriber.getFio(),
                        message.getCreateBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.forLanguageTag("ru"))),
                        message.getRoomName(),
                        message.getBeginBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.forLanguageTag("ru"))),
                        message.getEndBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.forLanguageTag("ru"))),
                        message.getFioOfBooking()
                );
    }

    private String textDelete(BookingNotify message, Subscriber subscriber) {
        return MessageFormat
                .format("{0}, {1} была удалена бронь с переговорки \"{2}\" с {3} по {4}. \r\n" +
                                "Автор изменений {5}.",
                        subscriber.getFio(),
                        message.getCreateBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.forLanguageTag("ru"))),
                        message.getRoomName(),
                        message.getBeginBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.forLanguageTag("ru"))),
                        message.getEndBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.forLanguageTag("ru"))),
                        message.getFioOfBooking()
                );
    }
}

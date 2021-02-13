package ru.otus.spring.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
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
public class TransformSmsMessageService implements TransformMessageService<MessageCreator> {

    private final NotificationProperties notificationProperties;

    @Override
    public MessageCreator[] transform(BookingNotify bookingNotify) {
        Twilio.init(notificationProperties.getSms().getTwilioAccountSid(), notificationProperties.getSms().getTwilioAuthToken());

        return bookingNotify.getSubscribers().stream()
                .filter(Subscriber::isPhoneNotify)
                .map(profileUserDto -> {
                    MessageCreator message = new MessageCreator(new PhoneNumber(profileUserDto.getMobilePhone()),
                            new PhoneNumber(notificationProperties.getSms().getTwilioPhoneNumber()),
                            "");
                    if (bookingNotify.getDeleteBookingDate() != null) {
                        message.setBody(textDelete(bookingNotify, profileUserDto));
                    } else if (bookingNotify.getUpdateBookingDate() != null) {
                        message.setBody(textUpdate(bookingNotify, profileUserDto));
                    } else {
                        message.setBody(textCreate(bookingNotify, profileUserDto));
                    }
                    return message;
                })
                .toArray(MessageCreator[]::new);
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
                        message.getUpdateBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
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
                        message.getDeleteBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
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

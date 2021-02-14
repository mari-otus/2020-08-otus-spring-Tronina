package ru.otus.spring.service;

import lombok.experimental.UtilityClass;
import ru.otus.spring.model.BookingNotificationReminder;
import ru.otus.spring.model.BookingNotify;
import ru.otus.spring.model.Subscriber;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * @author MTronina
 */
@UtilityClass
public class MessageUtils {

    public String textReminder(BookingNotificationReminder message) {
        return MessageFormat
                .format("Уважаемый {0}, в {1} начнется Ваша бронь на переговорку \"{2}\".",
                        message.getSubscriber().getFio(),
                        message.getBeginBookingDate().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
                                .withLocale(Locale.forLanguageTag("ru"))),
                        message.getRoomName()
                );
    }

    public String textCreate(BookingNotify message, Subscriber subscriber) {
        return MessageFormat
                .format("Уважаемый {0}, {1} была забронирована переговорка \"{2}\" на период с {3} по {4}. \r\n" +
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

    public String textUpdate(BookingNotify message, Subscriber subscriber) {
        return MessageFormat
                .format("Уважаемый {0}, {1} была изменена бронь переговорки \"{2}\". \r\n" +
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

    public String textDelete(BookingNotify message, Subscriber subscriber) {
        return MessageFormat
                .format("Уважаемый {0}, {1} была удалена бронь с переговорки \"{2}\" с {3} по {4}. \r\n" +
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

package ru.otus.spring.service;

import ru.otus.spring.model.BookingNotificationReminder;
import ru.otus.spring.model.BookingNotify;

/**
 * @author MTronina
 */
public interface TransformMessageService<T> {

    T[] transform(BookingNotify message);

    T transformReminder(BookingNotificationReminder message);
}

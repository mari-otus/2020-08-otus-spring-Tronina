package ru.otus.spring.sheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import ru.otus.spring.dto.BookingDto;
import ru.otus.spring.listener.notification.NotifyManager;
import ru.otus.spring.service.BookingService;

import java.util.List;

/**
 * @author MTronina
 */
@RequiredArgsConstructor
public class SchedulerNotify {

    private final BookingService bookingService;
    private final NotifyManager notifyManager;

    @Value("${app.schedule.notify-minutes}")
    private Integer minutes;

    @Scheduled(cron = "${app.schedule.notify-bookings-cron-expression}")
    public void runNotifyReminder() {
        List<BookingDto> soonStartingBookings = bookingService.getSoonStartingBookings(minutes);
        notifyManager.notifyReminder(soonStartingBookings);
    }

}

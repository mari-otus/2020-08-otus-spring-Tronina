package ru.otus.spring.sheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import ru.otus.spring.dto.BookingResponseDto;
import ru.otus.spring.listener.notification.NotificationManager;
import ru.otus.spring.service.BookingService;

import java.util.Date;
import java.util.List;

/**
 * @author MTronina
 */
@RequiredArgsConstructor
public class SchedulerNotify {

    private final BookingService bookingService;
    private final NotificationManager notificationManager;

    @Value("${app.schedule.notify-bookings-cron-expression}")
    private String cronExpression;

    @Scheduled(cron = "${app.schedule.notify-bookings-cron-expression}")
    public void runNotifyReminder() {
        Date startDate = new Date();
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cronExpression);
        Date nextDate = cronSequenceGenerator.next(startDate);

        long periodMinutes = Math.round(Math.ceil((double)(nextDate.getTime() - startDate.getTime())/1000.0/60.0));

        List<BookingResponseDto> soonStartingBookings = bookingService.getSoonStartingBookings(periodMinutes);
        notificationManager.notifyReminder(soonStartingBookings);
    }

}

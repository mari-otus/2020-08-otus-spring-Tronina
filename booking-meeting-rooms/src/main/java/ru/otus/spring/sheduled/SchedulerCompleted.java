/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2020 VTB Group. All rights reserved.
 */

package ru.otus.spring.sheduled;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import ru.otus.spring.service.BookingService;

/**
 * Планировщик для перевода закончившихся броней в статус удаленных.
 *
 * @author MTronina
 */
@RequiredArgsConstructor
public class SchedulerCompleted {

    private final BookingService bookingService;

    @Scheduled(cron = "${app.schedule.complete-bookings-cron-expression}")
    public void runCompleteBookings() {
        bookingService.completedBookings();
    }

}

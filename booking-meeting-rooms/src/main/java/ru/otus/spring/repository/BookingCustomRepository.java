package ru.otus.spring.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.dto.BookingFilter;

import java.util.List;

/**
 * @author MTronina
 */
public interface BookingCustomRepository {

    Page<Booking> findAllByFilter(BookingFilter filter, Pageable pageable);

    List<Booking> findAllByFilter(BookingFilter filter);
}

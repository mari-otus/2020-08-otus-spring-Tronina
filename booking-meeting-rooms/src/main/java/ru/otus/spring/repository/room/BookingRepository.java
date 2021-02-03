package ru.otus.spring.repository.room;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Booking;

import java.util.List;

/**
 * Репозиторий для работы с бронированием переговорной комнаты.
 *
 * @author Mariya Tronina
 */
public interface BookingRepository extends JpaRepository<Booking, Long>, BookingCustomRepository {

    List<Booking> findAllByRoomIdAndDeleteDateIsNull(Long roomId);

}

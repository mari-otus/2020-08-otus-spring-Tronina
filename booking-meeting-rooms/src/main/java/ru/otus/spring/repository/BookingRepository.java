package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Booking;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с бронированием переговорной комнаты.
 *
 * @author Mariya Tronina
 */
public interface BookingRepository extends JpaRepository<Booking, Long>, BookingCustomRepository {

    List<Booking> findAllByRoomIdAndDeleteDateIsNull(Long roomId);

    Optional<Booking> findByIdEqualsAndAndLoginEquals(Long id, String login);

}

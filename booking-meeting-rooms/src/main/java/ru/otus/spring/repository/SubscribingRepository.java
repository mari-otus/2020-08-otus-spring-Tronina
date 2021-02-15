package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Subscribing;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с профилем пользователя.
 *
 * @author MTronina
 */
public interface SubscribingRepository extends JpaRepository<Subscribing, Long> {

    List<Subscribing> findAllByLoginEquals(String login);

    List<Subscribing> findAllByDeleteDateIsNull();

    List<Subscribing> findAllByRoom_Id(Long roomId);

    Optional<Subscribing> findByLoginEqualsAndRoomId(String login, Long roomId);
}

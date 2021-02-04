package ru.otus.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Room;

/**
 * Репозиторий для работы с переговорной комнатой.
 *
 * @author Mariya Tronina
 */
public interface RoomRepository extends JpaRepository<Room, Long>, RoomCustomRepository {

}

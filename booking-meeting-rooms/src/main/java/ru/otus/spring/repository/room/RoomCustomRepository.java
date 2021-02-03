package ru.otus.spring.repository.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.spring.domain.Room;
import ru.otus.spring.dto.RoomFilter;

/**
 * @author MTronina
 */
public interface RoomCustomRepository {

    Page<Room> findAllByFilter(RoomFilter filter, Pageable pageable);
}

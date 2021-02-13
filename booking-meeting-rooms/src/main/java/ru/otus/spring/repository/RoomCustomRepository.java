package ru.otus.spring.repository;

import ru.otus.spring.domain.Room;
import ru.otus.spring.dto.RoomFilter;

import java.util.List;

/**
 * @author MTronina
 */
public interface RoomCustomRepository {

    List<Room> findAllByFilter(RoomFilter filter);
}

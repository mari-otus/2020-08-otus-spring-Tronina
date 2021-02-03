package ru.otus.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.spring.dto.RoomDto;
import ru.otus.spring.dto.RoomFilter;

/**
 * @author MTronina
 */
public interface RoomService {

    void createRoom(RoomDto roomRequest);

    void updateRoom(Long roomId, RoomDto roomRequest);

    void deleteRoom(Long roomId);

    Page<RoomDto> getRooms(RoomFilter roomFilter, Pageable pageable);
}

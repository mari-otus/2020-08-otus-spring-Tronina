package ru.otus.spring.service;

import ru.otus.spring.dto.RoomFilter;
import ru.otus.spring.dto.RoomRequestDto;
import ru.otus.spring.dto.RoomResponseDto;

import java.util.List;

/**
 * @author MTronina
 */
public interface RoomService {

    void createRoom(RoomRequestDto roomRequest);

    void updateRoom(Long roomId, RoomRequestDto roomRequest);

    List<RoomResponseDto> deleteRoom(Long roomId);

    List<RoomResponseDto> getRooms(RoomFilter roomFilter);

    RoomResponseDto getRoom(Long roomId);

}

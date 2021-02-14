package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.domain.Room;
import ru.otus.spring.domain.Subscribing;
import ru.otus.spring.dto.RoomDto;
import ru.otus.spring.dto.RoomFilter;
import ru.otus.spring.dto.RoomResponseDto;
import ru.otus.spring.exception.ApplicationException;
import ru.otus.spring.mapper.BookingMapper;
import ru.otus.spring.repository.BookingRepository;
import ru.otus.spring.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper mapper;
    private final SubscribingService subscribingService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public void createRoom(RoomDto roomRequest) {
        Room room = mapper.toRoom(roomRequest);
        roomRepository.save(room);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public void updateRoom(Long roomId, RoomDto roomRequest) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(ApplicationException::new);
        if (roomRequest.getCapacity() != null) {
            room.setCapacity(roomRequest.getCapacity());
        }
        if (roomRequest.getRoomName() != null) {
            room.setRoomName(roomRequest.getRoomName());
        }
        room.setHasConditioner(roomRequest.isHasConditioner());
        room.setHasVideoconference(roomRequest.isHasVideoconference());
        room.setUpdateDate(LocalDateTime.now());
        roomRepository.save(room);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    @Override
    public List<RoomResponseDto> deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(ApplicationException::new);
        List<Booking> bookingList = bookingRepository.findAllByRoomIdAndDeleteDateIsNull(roomId);
        if (CollectionUtils.isNotEmpty(bookingList)) {
            throw new ApplicationException("Удаление невозможно. На эту переговорную комнату найдена активная бронь");
        }
        room.setDeleteDate(LocalDateTime.now());
        roomRepository.save(room);
        return getRooms(RoomFilter.builder().build());
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoomResponseDto> getRooms(RoomFilter roomFilter) {
        List<Subscribing> subscribeRoom = subscribingService.getSubscribeRoom();
        return roomRepository.findAllByFilter(roomFilter)
                .stream()
                .map(mapper::toRoomResponseDto)
                .map(roomResponseDto -> {
                    roomResponseDto.setHasSubscribe(
                            subscribeRoom.stream()
                                    .anyMatch(subscribing -> subscribing.getRoom().getId()
                                            .equals(roomResponseDto.getId()))
                    );
                    return roomResponseDto;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public RoomResponseDto getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .map(mapper::toRoomResponseDto)
                .orElseThrow(ApplicationException::new);
    }

}

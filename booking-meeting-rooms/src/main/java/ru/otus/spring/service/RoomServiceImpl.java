package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.domain.Room;
import ru.otus.spring.dto.RoomDto;
import ru.otus.spring.dto.RoomFilter;
import ru.otus.spring.exception.ApplicationException;
import ru.otus.spring.mapper.BookingMapper;
import ru.otus.spring.repository.BookingRepository;
import ru.otus.spring.repository.RoomRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper mapper;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void createRoom(RoomDto roomRequest) {
        Room room = mapper.toRoom(roomRequest);
        roomRepository.save(room);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
        if (roomRequest.getHasConditioner() != null) {
            room.setHasConditioner(roomRequest.getHasConditioner());
        }
        if (roomRequest.getHasVideoconference() != null) {
            room.setHasVideoconference(roomRequest.getHasVideoconference());
        }
        room.setUpdateDate(LocalDateTime.now());
        roomRepository.save(room);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(ApplicationException::new);
        List<Booking> bookingList = bookingRepository.findAllByRoomIdAndDeleteDateIsNull(roomId);
        if (CollectionUtils.isNotEmpty(bookingList)) {
            throw new ApplicationException("Room has booking!");
        }
        room.setDeleteDate(LocalDateTime.now());
        roomRepository.save(room);
    }

    @Override
    public Page<RoomDto> getRooms(RoomFilter roomFilter, Pageable pageable) {
        return roomRepository.findAllByFilter(roomFilter, pageable)
                .map(mapper::toRoomDto);
    }
}

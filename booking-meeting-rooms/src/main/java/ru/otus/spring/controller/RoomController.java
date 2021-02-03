package ru.otus.spring.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.RoomDto;
import ru.otus.spring.dto.RoomFilter;
import ru.otus.spring.service.RoomService;

/**
 * @author MTronina
 */
@Api(tags = "Сервис создания переговорок")
@RestController
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/rooms")
    public ResponseEntity<Void> createRoom(
            @RequestBody RoomDto room) {
        roomService.createRoom(room);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<Void> editRoom(
            @PathVariable Long roomId,
            @RequestBody RoomDto room) {
        roomService.updateRoom(roomId, room);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<Void> deleteRoom(
            @PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/rooms/search")
    public ResponseEntity<Page<RoomDto>> getRooms(
            @RequestBody RoomFilter roomFilter, Pageable pageable) {
        Page<RoomDto> rooms = roomService.getRooms(roomFilter, pageable);
        return ResponseEntity.ok(rooms);
    }
}

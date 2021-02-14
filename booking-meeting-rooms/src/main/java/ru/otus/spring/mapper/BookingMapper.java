package ru.otus.spring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.domain.Profile;
import ru.otus.spring.domain.Room;
import ru.otus.spring.dto.BookingResponseDto;
import ru.otus.spring.dto.ProfileDto;
import ru.otus.spring.dto.RoomRequestDto;
import ru.otus.spring.dto.RoomResponseDto;

/**
 * @author MTronina
 */
@Mapper
public interface BookingMapper {

    @Mapping(source = "emailNotify", target = "isEmailNotify")
    @Mapping(source = "phoneNotify", target = "isPhoneNotify")
    ProfileDto toProfileDto(Profile profile);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.roomName", target = "roomName")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "beginDate", target = "beginDate")
    @Mapping(source = "endDate", target = "endDate")
    BookingResponseDto toBookingDto(Booking booking);

    Room toRoom(RoomRequestDto roomDto);

    RoomResponseDto toRoomResponseDto(Room room);
}

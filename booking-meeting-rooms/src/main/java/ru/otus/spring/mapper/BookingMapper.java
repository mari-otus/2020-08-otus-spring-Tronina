package ru.otus.spring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.domain.Profile;
import ru.otus.spring.domain.Room;
import ru.otus.spring.dto.BookingDto;
import ru.otus.spring.dto.ProfileDto;
import ru.otus.spring.dto.RoomDto;

/**
 * @author MTronina
 */
@Mapper
public interface BookingMapper {

    ProfileDto toProfileDto(Profile profile);

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "login", target = "login")
    @Mapping(source = "beginDate", target = "beginDate")
    @Mapping(source = "endDate", target = "endDate")
    BookingDto toBookingDto(Booking booking);

    Room toRoom(RoomDto roomDto);
    RoomDto toRoomDto(Room room);
}

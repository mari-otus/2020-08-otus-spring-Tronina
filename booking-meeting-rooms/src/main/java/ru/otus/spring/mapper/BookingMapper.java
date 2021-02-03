package ru.otus.spring.mapper;

import org.mapstruct.Mapper;
import ru.otus.spring.domain.Booking;
import ru.otus.spring.domain.Profile;
import ru.otus.spring.domain.Room;
import ru.otus.spring.domain.User;
import ru.otus.spring.dto.BookingDto;
import ru.otus.spring.dto.ProfileDto;
import ru.otus.spring.dto.RoomDto;
import ru.otus.spring.dto.UserDto;

/**
 * @author MTronina
 */
@Mapper
public interface BookingMapper {

    UserDto toUserDto(User user);
    User toUser(UserDto user);

    ProfileDto toProfileDto(Profile profile);
    BookingDto toBookingDto(Booking booking);

    Room toRoom(RoomDto roomDto);
    RoomDto toRoomDto(Room room);
}

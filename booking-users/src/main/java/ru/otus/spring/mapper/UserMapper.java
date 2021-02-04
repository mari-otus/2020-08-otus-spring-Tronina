package ru.otus.spring.mapper;

import org.mapstruct.Mapper;
import ru.otus.spring.domain.User;
import ru.otus.spring.dto.UserDto;

/**
 * @author MTronina
 */
@Mapper
public interface UserMapper {

    UserDto toUserDto(User user);
    User toUser(UserDto user);

}

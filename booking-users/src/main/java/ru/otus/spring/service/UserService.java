package ru.otus.spring.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.otus.spring.dto.UserCreateDto;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.dto.UserUpdateDto;

/**
 * @author MTronina
 */
public interface UserService {

    Page<UserDto> getUsers(Pageable pageable);

    UserDto createUser(UserCreateDto user);

    void lockUser(Long userId);

    void unlockUser(Long userId);

    void enableUser(Long userId);

    void disableUser(Long userId);

    void editUser(UserUpdateDto user);

    UserDto getUserByLogin(String login);
}

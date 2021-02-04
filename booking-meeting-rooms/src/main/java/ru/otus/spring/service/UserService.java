package ru.otus.spring.service;

import ru.otus.spring.dto.UserDto;

/**
 * @author MTronina
 */
public interface UserService {

    UserDto getUserByLogin(String login);
}

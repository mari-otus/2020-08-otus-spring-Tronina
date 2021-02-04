package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.otus.spring.client.UserClient;
import ru.otus.spring.dto.UserDto;
import ru.otus.spring.exception.ApplicationException;

/**
 * @author MTronina
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    @Override
    public UserDto getUserByLogin(String login) {
        ResponseEntity<UserDto> response = userClient.getUserByLogin(login);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        throw new ApplicationException();
    }
}

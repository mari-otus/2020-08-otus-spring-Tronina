package ru.otus.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.client.UserClient;
import ru.otus.spring.model.UserDto;

/**
 * Сервис для получения данных пользователя.
 *
 * @author MTronina
 */
@RequiredArgsConstructor
@Service
public class DbUserDetailsService implements UserDetailsService {
    /**
     * Репозиторий для работы с пользователями.
     */
    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserDto user = userClient.getUserByLogin(login).getBody();
        return new AuthUserDetails(user);
    }
}

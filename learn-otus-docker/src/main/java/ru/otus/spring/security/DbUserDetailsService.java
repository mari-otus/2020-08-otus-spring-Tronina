package ru.otus.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.spring.repository.user.UserRepository;

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
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return new AuthUserDetails(userRepository.findByLogin(login));
    }
}

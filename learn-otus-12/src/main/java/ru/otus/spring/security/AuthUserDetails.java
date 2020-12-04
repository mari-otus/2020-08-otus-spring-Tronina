package ru.otus.spring.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.spring.domain.User;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Информация об аутентифицированном пользователе.
 *
 * @author MTronina
 */
@RequiredArgsConstructor
public class AuthUserDetails implements UserDetails {

    /**
     * Сущность пользователя.
     */
    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountExpired() == null || user.getAccountExpired().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getPasswordExpired() == null || user.getPasswordExpired().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}

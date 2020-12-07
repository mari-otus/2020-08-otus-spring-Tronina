package ru.otus.spring.security;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.otus.spring.domain.User;
import ru.otus.spring.domain.UserRole;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

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

    public String getFio() {
        return user.getFio();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return CollectionUtils.emptyIfNull(user.getRoles()).stream()
                .collect(Collectors.toList());
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

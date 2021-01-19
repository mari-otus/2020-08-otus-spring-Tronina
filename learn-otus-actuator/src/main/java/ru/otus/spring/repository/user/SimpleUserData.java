package ru.otus.spring.repository.user;

import org.springframework.data.rest.core.config.Projection;
import ru.otus.spring.domain.User;

import java.time.LocalDateTime;

/**
 * @author MTronina
 */
@Projection(name = "simpleUserData", types = {User.class})
public interface SimpleUserData {

    String getLogin();
    String getFio();
    boolean isLocked();
    LocalDateTime getAccountExpired();
    LocalDateTime getPasswordExpired();
    boolean isEnabled();

}

package ru.otus.spring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.domain.User;

/**
 * Репозиторий для работы с пользователями.
 *
 * @author MTronina
 */
@RepositoryRestResource(path = "users", excerptProjection = SimpleUserData.class)
public interface UserRepository extends JpaRepository<User, Long> {

    @RestResource(path = "login", rel = "login")
    User findByLogin(String login);
}

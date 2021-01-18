package ru.otus.spring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.User;

/**
 * Репозиторий для работы с пользователями.
 *
 * @author Mariya Tronina
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);
}

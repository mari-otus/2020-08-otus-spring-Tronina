package ru.otus.spring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.UserRole;

import java.util.Optional;

/**
 * Репозиторий для работы с ролями пользователя.
 *
 * @author Mariya Tronina
 */
public interface RoleRepository extends JpaRepository<UserRole, Long> {

    Optional<UserRole> findByRole(String role);
}

package ru.otus.spring.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Profile;

import java.util.Optional;

/**
 * Репозиторий для работы с профилем пользователя.
 *
 * @author Mariya Tronina
 */
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByUserId(Long userId);
}

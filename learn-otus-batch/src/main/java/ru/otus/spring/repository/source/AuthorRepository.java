package ru.otus.spring.repository.source;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.model.source.AuthorSource;

/**
 * Репозиторий для работы с книгами.
 *
 * @author MTronina
 */
public interface AuthorRepository extends JpaRepository<AuthorSource, Long> {
}

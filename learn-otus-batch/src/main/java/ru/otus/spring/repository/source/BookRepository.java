package ru.otus.spring.repository.source;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.model.source.BookSource;

/**
 * Репозиторий для работы с книгами.
 *
 * @author Mariya Tronina
 */
public interface BookRepository extends JpaRepository<BookSource, Long> {
}

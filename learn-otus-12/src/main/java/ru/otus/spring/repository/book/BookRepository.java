package ru.otus.spring.repository.book;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.domain.Book;

import java.util.List;

/**
 * Репозиторий для работы с книгами.
 *
 * @author MTronina
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph("author-genre-entity-graph")
    List<Book> findAllByOrderByIdAsc();

}

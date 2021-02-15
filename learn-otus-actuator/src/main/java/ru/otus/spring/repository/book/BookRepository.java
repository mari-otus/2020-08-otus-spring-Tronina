package ru.otus.spring.repository.book;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.otus.spring.domain.Book;

import java.util.List;

/**
 * Репозиторий для работы с книгами.
 *
 * @author Mariya Tronina
 */
@RepositoryRestResource(path = "books")
public interface BookRepository extends JpaRepository<Book, Long> {

    @RestResource(path = "all", rel = "all")
    @EntityGraph("author-genre-entity-graph")
    List<Book> findAllByOrderByIdAsc();

    @Query("select 1 from book")
    int check();

}

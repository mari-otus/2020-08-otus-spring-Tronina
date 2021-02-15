package ru.otus.spring.repository.book;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.otus.spring.domain.Book;

/**
 * Репозиторий для работы с книгами.
 *
 * @author MTronina
 */
public interface BookRepository extends ReactiveMongoRepository<Book, String> {

    Flux<Book> findAllByOrderByIdAsc();

}

package ru.otus.spring.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.mappers.BookMapper;
import ru.otus.spring.repository.book.BookRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.notFound;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * Обработчик запросов для книг.
 *
 * @author MTronina
 */
@RequiredArgsConstructor
@Component
public class BookHandler {

    /**
     * Репозиторий для работы с книгами.
     */
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    /**
     * Возвращает все книги.
     *
     * @param request входящий HTTP-запрос
     * @return список всех книг
     */
    public @NonNull Mono<ServerResponse> getAllBook(ServerRequest request) {
        return ok()
                .contentType(APPLICATION_JSON)
                .body(bookRepository.findAll()
                        .flatMap(book -> Mono.just(bookMapper.bookToBookDto(book))), BookDto.class)
                .switchIfEmpty(notFound().build());
    }

    /**
     * Удаляет книгу по идентификатору.
     *
     * @param request входящий HTTP-запрос, содержащий идентификатор
     * @return список оставшихся книг
     */
    public @NonNull Mono<ServerResponse> removeBook(ServerRequest request) {
        final String bookId = request.pathVariable("id");
        return ok()
                .contentType(APPLICATION_JSON)
                .body(bookRepository.deleteById(bookId)
                                .thenMany(bookRepository.findAllByOrderByIdAsc()
                                        .flatMap(book -> Mono.just(bookMapper.bookToBookDto(book)))),
                        BookDto.class);
    }

    /**
     * Возвращает книгу по идентификатору.
     *
     * @param request входящий HTTP-запрос, содержащий идентификатор
     * @return книгу
     */
    public @NonNull Mono<ServerResponse> getBook(ServerRequest request) {
        final String bookId = request.pathVariable("id");
        return bookRepository.findById(bookId)
                .flatMap(book -> Mono.just(bookMapper.bookToBookDto(book)))
                .flatMap(bookDto -> ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(bookDto))
                .switchIfEmpty(notFound().build());
    }

    /**
     * Сохраняет книгу.
     *
     * @param request входящий HTTP-запрос
     * @return книгу
     */
    public @NonNull Mono<ServerResponse> saveBook(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .flatMap(bookDto -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(bookRepository.save(bookMapper.bookDtoToBook(bookDto))
                                        .flatMap(book -> Mono.just(bookMapper.bookToBookDto(book))),
                                BookDto.class)
                );
    }
}

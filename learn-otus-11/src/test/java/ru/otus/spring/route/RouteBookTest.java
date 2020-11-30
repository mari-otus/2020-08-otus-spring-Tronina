package ru.otus.spring.route;

import lombok.SneakyThrows;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;

import java.util.Set;

/**
 * @author MTronina
 */
@DisplayName("RouteBookTest должен")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RouteBookTest {

    @Autowired
    private RouterFunction route;

    private WebTestClient client;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @BeforeEach
    public void setUp() {
        client = WebTestClient
                .bindToRouterFunction(route)
                .build();
    }

    @DisplayName("возвращать все книги")
    @Test
    void routeGetBooks() {
        var countBooks = 8;
        client.get()
                .uri(contextPath + "/books")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()").value(IsEqual.equalTo(countBooks));
    }

    @DisplayName("удалять книгу по идентификатору")
    @SneakyThrows
    @Test
    void routeDeleteBook() {
        var bookId = "1";
        var countBooksAfterRemove = 7;
        client.delete()
                .uri(contextPath + "/books/{id}", bookId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()").value(IsEqual.equalTo(countBooksAfterRemove))
                .jsonPath(String.format("$.[?(@.id == %s)]", bookId)).doesNotExist();
    }

    @DisplayName("возвращать книгу по идентификатору")
    @SneakyThrows
    @Test
    void routeGetBook() {
        var bookId = "1";
        client.get()
                .uri(contextPath + "/books/{id}", bookId)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath(String.format("$.[?(@.id == %s)]", bookId)).exists();
    }

    @DisplayName("возвращать HTTP 404, если нет книги по идентификатору")
    @SneakyThrows
    @Test
    void routeGetBookNotFound() {
        var bookId = "100";
        client.get()
                .uri(contextPath + "/books/{id}", bookId)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @DisplayName("сохранять книгу")
    @Test
    void routeSaveBook() {
        var book = BookDto.builder()
                .id(9L)
                .name("test")
                .yearEdition(2000)
                .genres(Set.of(Genre.builder().id("11").name("genre").build()))
                .authors(Set.of(Author.builder().id("15").fio("author").build()))
                .build();
        client.post()
                .uri(contextPath + "/books")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(book)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath(String.format("$.[?(@.id == %s)]", book.getId())).exists()
                .jsonPath("$.genres.length()").value(IsEqual.equalTo(book.getGenres().size()))
                .jsonPath("$.authors.length()").value(IsEqual.equalTo(book.getAuthors().size()));
    }
}

package ru.otus.spring.controller;

import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.otus.spring.controller.BookController;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.BookSaveDto;
import ru.otus.spring.mappers.BookMapper;
import ru.otus.spring.security.DbUserDetailsService;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.BookServiceImpl;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Тестирование контроллера для работы с книгами.
 *
 * @author MTronina
 */
@DisplayName("Контроллер BookController должен")
@WebMvcTest(value = {BookController.class, BookMapper.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbUserDetailsService dbUserDetailsService;
    @MockBean
    private BookServiceImpl bookService;

    private static final List<Book> BOOK_LIST = new ArrayList<>();
    private static final Long BOOK_ID_DEFAULT = 7L;

    @BeforeAll
    public void setUp() {
        BOOK_LIST.clear();
        BOOK_LIST.addAll(Stream.of(
                Book.builder().id(1L).name("Война и мир")
                        .yearEdition(1981)
                        .authors(Set.of(Author.builder().id(1L).fio("Толстой Лев Николаевич").build()))
                        .genres(Set.of(Genre.builder().id(1L).name("Роман").build()))
                        .build(),
                Book.builder().id(2L).name("Хаджи-Мурат")
                        .yearEdition(1975)
                        .authors(Set.of(Author.builder().id(1L).fio("Толстой Лев Николаевич").build()))
                        .genres(Set.of(Genre.builder().id(3L).name("Повесть").build()))
                        .build(),
                Book.builder().id(3L).name("Евгений Онегин")
                        .yearEdition(1987)
                        .authors(Set.of(Author.builder().id(2L).fio("Пушкин Александр Сергеевич").build()))
                        .genres(Set.of(Genre.builder().id(1L).name("Роман").build(), Genre.builder().id(4L).name("Стихотворение").build()))
                        .build(),
                Book.builder().id(4L).name("Сказка о рыбаке и рыбке")
                        .yearEdition(2015)
                        .authors(Set.of(Author.builder().id(2L).fio("Пушкин Александр Сергеевич").build()))
                        .genres(Set.of(Genre.builder().id(4L).name("Стихотворение").build(), Genre.builder().id(5L).name("Сказка").build()))
                        .build(),
                Book.builder().id(5L).name("Песнь о вещем Олеге")
                        .yearEdition(2015)
                        .authors(Set.of(Author.builder().id(2L).fio("Пушкин Александр Сергеевич").build()))
                        .genres(Set.of(Genre.builder().id(3L).name("Повесть").build(), Genre.builder().id(4L).name("Стихотворение").build()))
                        .build(),
                Book.builder().id(6L).name("Полное собрание повестей и рассказов о Шерлоке Холмсе в одном томе")
                        .yearEdition(1998)
                        .authors(Set.of(Author.builder().id(3L).fio("Дойл Артур Конан").build()))
                        .genres(Set.of(Genre.builder().id(2L).name("Рассказ").build(), Genre.builder().id(3L).name("Повесть").build()))
                        .build(),
                Book.builder().id(7L).name("Улитка на склоне")
                        .yearEdition(2001)
                        .authors(Set.of(Author.builder().id(4L).fio("Стругацкий Аркадий Натанович").build(), Author.builder().id(5L).fio("Стругацкий Борис Натанович").build()))
                        .genres(Set.of(Genre.builder().id(1L).name("Роман").build(), Genre.builder().id(8L).name("Фантастика").build()))
                        .build(),
                Book.builder().id(8L).name("Понедельник начинается в субботу")
                        .yearEdition(2001)
                        .authors(Set.of(Author.builder().id(4L).fio("Стругацкий Аркадий Натанович").build(), Author.builder().id(5L).fio("Стругацкий Борис Натанович").build()))
                        .genres(Set.of(Genre.builder().id(3L).name("Повесть").build(), Genre.builder().id(8L).name("Фантастика").build(), Genre.builder().id(9L).name("Юмор").build()))
                        .build()
        ).collect(Collectors.toList()));
    }

    @DisplayName("возвращать все книги")
    @WithMockUser(
            username = "admin"
    )
    @Test
    public void shouldGetAllBooks() throws Exception {
        given(bookService.getAllBook()).willReturn(BOOK_LIST);
        mockMvc.perform(get( "/books"))
                .andExpect(status().isOk());
    }


    @DisplayName("возвращать книгу по идентификатору")
    @WithMockUser(
            username = "admin"
    )
    @Test
    public void shouldGetBookById() throws Exception {
        given(bookService.getBook(anyLong())).willReturn(BOOK_LIST.stream()
                .filter(book -> book.getId() == BOOK_ID_DEFAULT)
                .findAny().get());
        mockMvc.perform(get("/books/{id}", BOOK_ID_DEFAULT))
        .andExpect(status().isOk());
    }


    @DisplayName("изменять существующую книгу")
    @WithMockUser(
            username = "admin"
    )
    @Test
    public void shouldEditBook() throws Exception {
        mockMvc.perform(post( "/books/{id}", BOOK_ID_DEFAULT, BookSaveDto.builder()))
                .andExpect(status().isFound());
    }

}

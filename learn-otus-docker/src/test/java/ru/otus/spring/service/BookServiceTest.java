package ru.otus.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.spring.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Тестирование контроллера для работы с книгами.
 *
 * @author Mariya Tronina
 */
@DisplayName("Контроллер BookService должен")
@SpringBootTest
@ContextConfiguration
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookServiceTest {

    public static final long BOOK_HOME_ID = 1L;
    public static final long BOOK_FOREIGN_ID = 2L;
    public static final int SIZE_ALL_BOOKS = 2;
    public static final int SIZE_ALL_FOREIGN_BOOKS = 1;
    public static final int SIZE_ALL_HOME_BOOKS = 1;

    @Autowired
    private BookServiceImpl bookService;

    @DisplayName("возвращать все книги для пользователя manager_all")
    @WithUserDetails(
            value = "manager_all",
            userDetailsServiceBeanName = "dbUserDetailsService"
    )
    @Test
    public void shouldGetAllBooksForManager() {
        assertThat(bookService.getAllBook())
                .isNotNull()
                .isNotEmpty()
                .hasSize(SIZE_ALL_BOOKS);
    }

    @DisplayName("возвращать все книги зарубежной литературы для пользователя manager_foreign")
    @WithUserDetails(
            value = "manager_foreign",
            userDetailsServiceBeanName = "dbUserDetailsService"
    )
    @Test
    public void shouldGetAllBooksForManagerForeign() {
        assertThat(bookService.getAllBook())
                .isNotNull()
                .isNotEmpty()
                .hasSize(SIZE_ALL_FOREIGN_BOOKS);
    }

    @DisplayName("возвращать все книги отечественной литературы для пользователя manager_home")
    @WithUserDetails(
            value = "manager_home",
            userDetailsServiceBeanName = "dbUserDetailsService"
    )
    @Test
    public void shouldGetAllBooksForManagerHome() {
        assertThat(bookService.getAllBook())
                .isNotNull()
                .isNotEmpty()
                .hasSize(SIZE_ALL_HOME_BOOKS);
    }

    @DisplayName("изменять отечественную книгу для пользователя manager_home")
    @WithUserDetails(
            value = "manager_home",
            userDetailsServiceBeanName = "dbUserDetailsService"
    )
    @Test
    public void shouldSaveBookForManagerHome() {
        Book book = bookService.getBook(BOOK_HOME_ID);
        book.setName("test");
        assertThat(bookService.addBook(book))
                .isNotNull()
                .isEqualToComparingFieldByField(book);
    }

    @DisplayName("изменять зарубежную книгу для пользователя manager_foreign")
    @WithUserDetails(
            value = "manager_foreign",
            userDetailsServiceBeanName = "dbUserDetailsService"
    )
    @Test
    public void shouldSaveBookForManagerForeign() {
        Book book = bookService.getBook(BOOK_FOREIGN_ID);
        book.setName("test");
        assertThat(bookService.addBook(book))
                .isNotNull()
                .isEqualToComparingFieldByField(book);
    }

    @DisplayName("пробрасывать AccessDeniedException при изменении любой книги для пользователя manager_all")
    @WithUserDetails(
            value = "manager_all",
            userDetailsServiceBeanName = "dbUserDetailsService"
    )
    @Test
    public void shouldSaveBookForManageAllError() {
        Book bookHome = bookService.getBook(BOOK_HOME_ID);
        bookHome.setName("test");
        assertThatThrownBy(() -> bookService.addBook(bookHome))
                .isInstanceOf(AccessDeniedException.class);

        Book bookForeign = bookService.getBook(BOOK_FOREIGN_ID);
        bookForeign.setName("test");
        assertThatThrownBy(() -> bookService.addBook(bookForeign))
                .isInstanceOf(AccessDeniedException.class);
    }

    @DisplayName("пробрасывать AccessDeniedException при попытке получить зарубежную книгу для пользователя manager_home")
    @WithUserDetails(
            value = "manager_home",
            userDetailsServiceBeanName = "dbUserDetailsService"
    )
    @Test
    public void shouldSaveBookForManagerHomeError() {
        assertThatThrownBy(() -> bookService.getBook(BOOK_FOREIGN_ID))
                .isInstanceOf(AccessDeniedException.class);
    }

    @DisplayName("пробрасывать AccessDeniedException при попытке получить отечественную книгу для пользователя manager_foreign")
    @WithUserDetails(
            value = "manager_foreign",
            userDetailsServiceBeanName = "dbUserDetailsService"
    )
    @Test
    public void shouldSaveBookForManagerForeignError() {
        assertThatThrownBy(() -> bookService.getBook(BOOK_HOME_ID))
                .isInstanceOf(AccessDeniedException.class);
    }

}

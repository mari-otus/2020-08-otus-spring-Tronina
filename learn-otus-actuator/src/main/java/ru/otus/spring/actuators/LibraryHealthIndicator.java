package ru.otus.spring.actuators;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Book;
import ru.otus.spring.service.BookService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LibraryHealthIndicator implements HealthIndicator {

    /**
     * Сервис для работы с книгами.
     */
    private final BookService bookService;

    @Override
    public Health health() {
        List<Book> allBook = bookService.getAllBook();
        if (CollectionUtils.isEmpty(allBook)) {
            return Health.down()
                    .status(Status.DOWN)
                    .withDetail("message", "Библиотека пуста или доступ к метрике запрещен (требуется авторизация)")
                    .build();
        } else {
            return Health.up()
                    .withDetail("message", "В библиотеке есть книги")
                    .build();
        }
    }
}

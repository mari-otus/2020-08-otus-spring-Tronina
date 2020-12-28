package ru.otus.spring.exception.book;

import ru.otus.spring.exception.ApplicationException;

/**
 * Исключение, если книга не найдена.
 *
 * @author Mariya Tronina
 */
public class BookNotFoundException extends ApplicationException {

    private static final String BOOK_NOT_FOUND_MESSAGE = "Книга не найдена";

    public BookNotFoundException() {
        super(BOOK_NOT_FOUND_MESSAGE);
    }
}

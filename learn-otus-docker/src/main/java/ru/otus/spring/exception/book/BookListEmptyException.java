package ru.otus.spring.exception.book;

import ru.otus.spring.exception.ApplicationException;

/**
 * Исключение, если список книг пуст.
 *
 * @author Mariya Tronina
 */
public class BookListEmptyException extends ApplicationException {

    private static final String BOOKS_LIST_EMPTY_MESSAGE = "Список книг пуст";

    public BookListEmptyException() {
        super(BOOKS_LIST_EMPTY_MESSAGE);
    }
}

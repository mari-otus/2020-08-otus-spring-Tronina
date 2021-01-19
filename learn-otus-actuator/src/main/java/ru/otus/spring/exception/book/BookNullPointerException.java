package ru.otus.spring.exception.book;

import ru.otus.spring.exception.ApplicationException;

/**
 * Исключение, если в обработку передается книга = null.
 *
 * @author Mariya Tronina
 */
public class BookNullPointerException extends ApplicationException {

    private static final String BOOK_NULL_POINTER_MESSAGE = "Книга не может быть null";

    public BookNullPointerException() {
        super(BOOK_NULL_POINTER_MESSAGE);
    }
}

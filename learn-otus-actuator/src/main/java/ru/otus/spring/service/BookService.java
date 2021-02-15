package ru.otus.spring.service;

import ru.otus.spring.domain.Book;

import java.util.List;

/**
 * Сервис для работы с книгами.
 *
 * @author MTronina
 */
public interface BookService {

    /**
     * Добавляет книгу.
     *
     * @param book книга
     * @return идентификатор книги
     */
    Book addBook(Book book);

    /**
     * Возвращает книгу по идентификатору.
     *
     * @param bookId идентификатор книги
     * @return книга
     */
    Book getBook(Long bookId);

    /**
     * Возвращает список всех книг.
     *
     * @return список книг
     */
    List<Book> getAllBook();

}

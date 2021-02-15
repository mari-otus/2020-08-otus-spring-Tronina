package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Book;
import ru.otus.spring.exception.book.BookListEmptyException;
import ru.otus.spring.exception.book.BookNotFoundException;
import ru.otus.spring.exception.book.BookNullPointerException;
import ru.otus.spring.repository.book.BookRepository;

import java.util.List;

/**
 * Реализация сервиса для работы с книгами.
 *
 * @author MTronina
 */
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    /**
     * Репозиторий для работы с книгами.
     */
    private final BookRepository bookRepository;

    @PreAuthorize("hasPermission(#book, 'WRITE')")
    @Transactional
    @Override
    public Book addBook(final Book book) {
        if (book == null) {
            throw new BookNullPointerException();
        }
        return bookRepository.save(book);
    }

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    @Transactional(readOnly = true)
    @Override
    public Book getBook(final Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

    @PostFilter("hasPermission(filterObject, 'READ')")
    @Transactional(readOnly = true)
    @Override
    public List<Book> getAllBook() {
        List<Book> books = bookRepository.findAllByOrderByIdAsc();
        if (CollectionUtils.isEmpty(books)) {
            throw new BookListEmptyException();
        }
        return books;
    }

}

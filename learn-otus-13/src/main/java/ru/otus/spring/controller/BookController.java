package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookSaveDto;
import ru.otus.spring.mappers.BookMapper;
import ru.otus.spring.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для работы с книгами.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@Controller
public class BookController {

    /**
     * Сервис для работы с книгами.
     */
    private final BookService bookService;

    private final BookMapper bookMapper;

    /**
     * Переход на главную страницу.
     *
     * @return view name.
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Возвращает все книги.
     *
     * @return view name.
     */
    @GetMapping("/books")
    public String getHome(Model model) {
        List<Book> books = bookService.getAllBook();
        model.addAttribute("books", books.stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList()));
        return "books";
    }

    /**
     * Переход на страницу редактирования книги.
     *
     * @return view name.
     */
    @GetMapping("/books/{id}")
    public String editBook(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", bookService.getBook(id));
        return "edit";
    }

    /**
     * Изменяет книгу по идентификатору.
     *
     * @param id идентификатор книги
     * @return view name
     */
    @PostMapping("/books/{id}")
    public String saveBook(@PathVariable long id, BookSaveDto bookSaveDto) {
        Book bookExists = bookService.getBook(id);
        bookService.addBook(bookMapper.bookSaveDtoToBook(bookExists, bookSaveDto));
        return "redirect:/books";
    }

}

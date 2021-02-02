package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Genre;

import java.util.Set;

/**
 * Книга.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    /**
     * Идентификатор книги.
     */
    private Long id;
    /**
     * Наименование книги.
     */
    private String name;
    /**
     * Год издания книги.
     */
    private int yearEdition;
    /**
     * Список авторов книги.
     */
    private Set<Author> authors;
    /**
     * Список жанров, к которым относится книга.
     */
    private Set<Genre> genres;

}

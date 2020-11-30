package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Книга.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("books")
public class Book {

    /**
     * Идентификатор книги.
     */
    @Id
    private String id;
    /**
     * Наименование книги.
     */
    @Field(name = "name")
    private String name;
    /**
     * Год издания книги.
     */
    @Field(name = "yearEdition")
    private int yearEdition;
    /**
     * Список авторов книги.
     */
    @Field(value = "authors")
    private List<Author> authors;
    /**
     * Список жанров, к которым относится книга.
     */
    @Field(value = "genres")
    private List<Genre> genres;
}

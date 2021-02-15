package ru.otus.spring.model.targets;

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
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("books")
public class BookTarget {

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
    private List<AuthorTarget> authors;
    /**
     * Список жанров, к которым относится книга.
     */
    @Field(value = "genres")
    private List<GenreTarget> genres;
}

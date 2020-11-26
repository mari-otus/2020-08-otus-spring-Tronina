package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Автор книги.
 *
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("authors")
public class Author {

    /**
     * Идентификатор автора.
     */
    @Id
    private String id;
    /**
     * Фамилия имя отчество автора.
     */
    @Field(name = "fio")
    private String fio;

}

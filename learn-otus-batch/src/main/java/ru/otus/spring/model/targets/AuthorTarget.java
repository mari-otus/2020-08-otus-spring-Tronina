package ru.otus.spring.model.targets;

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
public class AuthorTarget {

    /**
     * Идентификатор автора.
     */
    @Id
    private String id;

    @Field(name = "sourceId")
    private Long sourceId;

    /**
     * Фамилия имя отчество автора.
     */
    @Field(name = "fio")
    private String fio;

}

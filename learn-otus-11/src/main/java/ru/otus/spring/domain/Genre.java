package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Жанр произведения.
 *
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("genres")
public class Genre {

    /**
     * Идентификатор жанра.
     */
    @Id
    private String id;
    /**
     * Наименование жанра.
     */
    @Field(name = "name")
    private String name;
}

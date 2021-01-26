package ru.otus.spring.model.targets;

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
 * @author Mariya Tronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("genres")
public class GenreTarget {

    /**
     * Идентификатор жанра.
     */
    @Id
    private String id;

    @Field(name = "sourceId")
    private Long sourceId;

    /**
     * Наименование жанра.
     */
    @Field(name = "name")
    private String name;
}

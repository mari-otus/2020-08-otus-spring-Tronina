package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Книга.
 *
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookSaveDto {

    /**
     * Наименование книги.
     */
    private String name;
    /**
     * Год издания книги.
     */
    private int yearEdition;

}

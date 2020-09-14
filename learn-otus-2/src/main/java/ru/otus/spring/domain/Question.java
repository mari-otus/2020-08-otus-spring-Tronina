package ru.otus.spring.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект вопроса для теста.
 *
 * @author Mariya Tronina
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "text", "answers" })
public class Question {

    /**
     * Вопрос теста.
     */
    private String text;
    /**
     * Варианты ответов, разделенные запятой.
     */
    private String answers;

}

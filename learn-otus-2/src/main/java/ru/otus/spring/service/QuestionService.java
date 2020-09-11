package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

import java.util.List;

/**
 * Сервис для работы со списком вопросов.
 *
 * @author Mariya Tronina
 */
public interface QuestionService {

    /**
     * Возвращает список вопросов для тестирования.
     *
     * @return список вопросов
     */
    List<Question> getQuestionList();

}

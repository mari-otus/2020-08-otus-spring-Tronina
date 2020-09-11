package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.util.List;

/**
 * DAO для работы с вопросами для теста.
 *
 * @author Mariya Tronina
 */
public interface QuestionDao {

    /**
     * Возвращает список вопросов для тестирования.
     *
     * @return список вопросов
     */
    List<Question> findAllQuestion();
}

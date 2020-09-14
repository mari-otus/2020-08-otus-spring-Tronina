package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.domain.Question;

import java.util.Arrays;
import java.util.List;

/**
 * Сервис для запуска тестирования.
 *
 * @author Mariya Tronina
 */
@RequiredArgsConstructor
@Service
public class QuestionaryServiceImpl implements QuestionaryService {

    public static final String WELCOME = "Укажите Ваши фамилию и имя:";
    public static final String INCORRECT_VARIANT = "Вы выбрали неверный вариант ответа. Попробуйте ещё раз: ";
    public static final String YOU_ANSWER = "Ваш ответ: ";
    public static final String RESUME = "%s, Вы успешно прошли тест. \r\n" +
            "Вы ответили на %s вопросов из %s";
    public static final String ERROR_TEST = "Ошибка. Не удалось получить вопросы для теста";

    /**
     * Счетчик валидных ответов.
     */
    private int countAnswer = 0;

    /**
     * Сервис для работы со списком вопросов.
     */
    private final QuestionService questionService;

    /**
     * Сервис для работы с сообщениями.
     */
    private final MessageService messageService;

    @Override
    public void runQuestionary() {
        List<Question> questionList = questionService.getQuestionList();
        if (CollectionUtils.isEmpty(questionList)) {
            messageService.outMessage(ERROR_TEST);
            return;
        }
        messageService.outMessage(WELCOME);
        String fio = messageService.getMessage();
        questionList.forEach(
                    question -> {
                        if (!StringUtils.isEmpty(question.getAnswers())) {
                            List<String> answerList = Arrays.asList(question.getAnswers().split(","));
                            messageService.outMessage(question.getText());
                            answerList.forEach(messageService::outMessage);

                            String variant = messageService.getMessage();
                            while (!variant.matches("\\d+") ||
                                    Integer.valueOf(variant) > answerList.size()) {
                                messageService.outMessage(INCORRECT_VARIANT);
                                variant = messageService.getMessage();
                            }
                            if (Integer.valueOf(variant) > 0) {
                                messageService.outMessage(YOU_ANSWER + variant);
                                incCountAnswer();
                            }
                        } else {
                            messageService.outMessage(question.getText());
                            String answer = messageService.getMessage();
                            if (answer != null && answer.length() > 0) {
                                messageService.outMessage(YOU_ANSWER + answer);
                                incCountAnswer();
                            }
                        }
                    }
            );
        messageService.outMessage(String.format(RESUME, fio, countAnswer, questionList.size()));
        messageService.close();
    }

    /**
     * Увеличивает счетчик валидных ответов на 1.
     */
    private void incCountAnswer() {
        countAnswer ++;
    }

}

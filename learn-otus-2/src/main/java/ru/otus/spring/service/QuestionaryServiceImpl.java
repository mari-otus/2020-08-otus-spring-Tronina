package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import ru.otus.spring.domain.Question;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Сервис для запуска тестирования.
 *
 * @author MTronina
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

        Scanner scanner = new Scanner(messageService.getInputStream());

        messageService.outMessage(WELCOME);
        String fio = scanner.nextLine();
        questionList.forEach(
                    question -> {
                        if (!StringUtils.isEmpty(question.getAnswers())) {
                            List<String> answerList = Arrays.asList(question.getAnswers().split(","));
                            messageService.outMessage(question.getText());
                            answerList.forEach(messageService::outMessage);

                            String variant = scanner.nextLine();
                            while (!variant.matches("\\d+") ||
                                    Integer.parseInt(variant) > answerList.size()) {
                                messageService.outMessage(INCORRECT_VARIANT);
                                variant = scanner.nextLine();
                            }
                            if (Integer.parseInt(variant) > 0) {
                                messageService.outMessage(YOU_ANSWER + variant);
                                incCountAnswer();
                            }
                        } else {
                            messageService.outMessage(question.getText());
                            String answer = scanner.nextLine();
                            if (answer != null && answer.length() > 0) {
                                messageService.outMessage(YOU_ANSWER + answer);
                                incCountAnswer();
                            }
                        }
                    }
            );
        messageService.outMessage(String.format(RESUME, fio, countAnswer, questionList.size()));
        scanner.close();
    }

    /**
     * Увеличивает счетчик валидных ответов на 1.
     */
    private void incCountAnswer() {
        countAnswer ++;
    }

}

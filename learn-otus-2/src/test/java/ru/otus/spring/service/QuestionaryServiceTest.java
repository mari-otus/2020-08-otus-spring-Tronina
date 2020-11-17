package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Question;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static ru.otus.spring.service.QuestionaryServiceImpl.ERROR_TEST;
import static ru.otus.spring.service.QuestionaryServiceImpl.INCORRECT_VARIANT;
import static ru.otus.spring.service.QuestionaryServiceImpl.RESUME;
import static ru.otus.spring.service.QuestionaryServiceImpl.WELCOME;
import static ru.otus.spring.service.QuestionaryServiceImpl.YOU_ANSWER;

/**
 * Тест для сервиса тестирования.
 *
 * @author Mariya Tronina
 */
@DisplayName("QuestionaryService должен")
@ExtendWith(MockitoExtension.class)
class QuestionaryServiceTest {

    @Mock
    private QuestionService questionService;
    private QuestionaryService questionaryService;

    private static final List<Question> questionList = new ArrayList<>();

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream printStream = new PrintStream(outputStream);

    @BeforeAll
    public static void setUp() {
        questionList.add(
                Question.builder()
                        .text("Вопрос 1")
                        .build()
        );
        questionList.add(
                Question.builder()
                        .text("Вопрос 2")
                        .answers("да,нет")
                        .build()
        );
    }

    @DisplayName("сообщить об ошибке, если не найдены вопросы для теста")
    @Test
    void runQuestionaryWithError() {
        questionaryService = new QuestionaryServiceImpl(questionService,
                new MessageServiceImpl(new ByteArrayInputStream("".getBytes()), printStream));

        when(questionService.getQuestionList()).thenReturn(null);
        questionaryService.runQuestionary();

        assertThat(outputStream.toString())
                .isNotEmpty()
                .isEqualTo(ERROR_TEST + "\r\n");
    }

    @DisplayName("выводить заданные вопросы и анализировать ответы")
    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("initParam")
    void runQuestionary(String title, String answers, String result) {
        questionaryService = new QuestionaryServiceImpl(questionService,
                new MessageServiceImpl(new ByteArrayInputStream(answers.getBytes()), printStream));

        when(questionService.getQuestionList()).thenReturn(questionList);
        questionaryService.runQuestionary();

        assertThat(outputStream.toString())
                .isNotEmpty()
                .isEqualTo(result);
    }

    /**
     * Список ответов и соответсвующий им вывод системы тестирования.
     *
     * @return список параметров
     */
    private static Stream<Arguments> initParam() {
        return Stream.of(
                Arguments.of(
                        "все ответы корректные",
                        "fio\n" +
                        "answer\n" +
                        "1\n",
                        WELCOME + "\r\n" +
                        questionList.get(0).getText() + "\r\n" +
                        YOU_ANSWER + "answer\r\n" +
                        questionList.get(1).getText() + "\r\n" +
                        questionList.get(1).getAnswers().split(",")[0] + "\r\n" +
                        questionList.get(1).getAnswers().split(",")[1] + "\r\n" +
                        YOU_ANSWER + "1\r\n" +
                        String.format(RESUME, "fio", 2, 2) + "\r\n"),
                Arguments.of(
                        "содержит неправильный вариант ответа в случае выбра ответа из списка",
                        "fio\n" +
                        "answer\n" +
                        "3\n" +
                        "ne_chislo\n" +
                        "2\n",
                        WELCOME + "\r\n" +
                        questionList.get(0).getText() + "\r\n" +
                        YOU_ANSWER + "answer\r\n" +
                        questionList.get(1).getText() + "\r\n" +
                        questionList.get(1).getAnswers().split(",")[0] + "\r\n" +
                        questionList.get(1).getAnswers().split(",")[1] + "\r\n" +
                        INCORRECT_VARIANT + "\r\n" +
                        INCORRECT_VARIANT + "\r\n" +
                        YOU_ANSWER + "2\r\n" +
                        String.format(RESUME, "fio", 2, 2) + "\r\n"),
                Arguments.of(
                        "содержит отсутствующий ответ",
                        "fio\n" +
                        "answer\n" +
                        "0\n",
                        WELCOME + "\r\n" +
                        questionList.get(0).getText() + "\r\n" +
                        YOU_ANSWER + "answer\r\n" +
                        questionList.get(1).getText() + "\r\n" +
                        questionList.get(1).getAnswers().split(",")[0] + "\r\n" +
                        questionList.get(1).getAnswers().split(",")[1] + "\r\n" +
                        String.format(RESUME, "fio", 1, 2) + "\r\n")
        );
    }
}

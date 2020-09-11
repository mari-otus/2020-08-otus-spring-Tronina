package ru.otus.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.service.QuestionaryService;

@ComponentScan
public class Main {

    public static void main(String[] args) {
        ApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class);

        QuestionaryService questionaryService = context.getBean(QuestionaryService.class);

        //запускаем сервис тестирования
        questionaryService.runQuestionary();

    }
}

package ru.otus.spring.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * Сервис для работы с сообщениями.
 *
 * @author Mariya Tronina
 */
@Data
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final InputStream inputStream;
    private final PrintStream out;

    @Override
    public void outMessage(String message) {
        out.println(message);
    }

}

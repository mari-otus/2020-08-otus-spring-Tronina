package ru.otus.spring.service;

import java.io.InputStream;

/**
 * Сервис для работы с сообщениями.
 *
 * @author Mariya Tronina
 */
public interface MessageService {

    /**
     * Выводит сообщение в заданный поток.
     *
     * @param message сообщение
     */
    void outMessage(String message);

    /**
     * Возвращает входной поток.
     *
     * @return входной поток
     */
    InputStream getInputStream();

}

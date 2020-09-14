package ru.otus.spring.service;

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
     * Получает сообщение из потока.
     *
     * @return сообщение
     */
    String getMessage();

    /**
     * Закрывает поток.
     */
    void close();
}

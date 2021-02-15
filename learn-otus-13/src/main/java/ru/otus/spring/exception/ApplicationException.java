package ru.otus.spring.exception;

/**
 * Базовое исключение.
 *
 * @author MTronina
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }
}

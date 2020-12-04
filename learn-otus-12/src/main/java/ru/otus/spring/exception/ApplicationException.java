package ru.otus.spring.exception;

/**
 * Базовое исключение.
 *
 * @author Mariya Tronina
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException(String message) {
        super(message);
    }
}

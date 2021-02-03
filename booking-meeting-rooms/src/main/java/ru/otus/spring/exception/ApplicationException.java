package ru.otus.spring.exception;

/**
 * Базовое исключение.
 *
 * @author Mariya Tronina
 */
public class ApplicationException extends RuntimeException {

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }
}

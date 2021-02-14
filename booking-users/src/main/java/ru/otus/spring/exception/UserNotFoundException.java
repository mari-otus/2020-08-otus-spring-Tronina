package ru.otus.spring.exception;

import java.text.MessageFormat;

/**
 * Базовое исключение.
 *
 * @author Mariya Tronina
 */
public class UserNotFoundException extends ApplicationException {

    public static final String TEMPLATE_MESSAGE_ERROR_BY_ID = "Пользователь с идентификатором {0} не найден";
    public static final String TEMPLATE_MESSAGE_ERROR_BY_LOGIN = "Пользователь с логином {0} не найден";

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(Long userId) {
        super(MessageFormat.format(TEMPLATE_MESSAGE_ERROR_BY_ID, userId));
    }

    public UserNotFoundException(String login) {
        super(MessageFormat.format(TEMPLATE_MESSAGE_ERROR_BY_LOGIN, login));
    }
}

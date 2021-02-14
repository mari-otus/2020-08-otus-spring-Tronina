package ru.otus.spring.exception;

import java.text.MessageFormat;

/**
 * Базовое исключение.
 *
 * @author Mariya Tronina
 */
public class RoleNotFoundException extends ApplicationException {

    public static final String TEMPLATE_MESSAGE_ERROR = "Роль {0} не найдена";

    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(String role) {
        super(MessageFormat.format(TEMPLATE_MESSAGE_ERROR, role));
    }
}

package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Пользователь.
 *
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {

    /**
     * Логин пользователя.
     */
    private String login;

    /**
     * ФИО пользователя.
     */
    private String fio;

    /**
     * Признак блокировки пользователя: false - не заблокирован, true - заблокирован.
     */
    private boolean locked;

    /**
     * Дата и время окончания срока действия учетной записи пользователя.
     */
    private LocalDateTime accountExpired;

    /**
     * Дата и время окончания срока действия пароля пользователя.
     */
    private LocalDateTime passwordExpired;

    /**
     * Признак активности пользователя: false - не активен, true - активен.
     */
    private boolean enabled;

    /**
     * Список ролей пользователя.
     */
    private Set<UserRoleDto> roles;
}

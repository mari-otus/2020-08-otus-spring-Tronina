package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Пользователь.
 *
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    /**
     * Идентификатор пользователя.
     */
    private Long id;

    /**
     * Логин пользователя.
     */
    private String login;

    /**
     * ФИО пользователя.
     */
    private String fio;

}

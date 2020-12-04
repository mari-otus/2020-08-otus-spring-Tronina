package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Пользователь.
 *
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_library")
public class User {

    /**
     * Идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Логин пользователя.
     */
    @Column(name = "login", nullable = false)
    private String login;

    /**
     * Пароль пользователя.
     */
    @Column(name = "password", nullable = false)
    private String password;

    /**
     * Признак блокировки пользователя: false - не заблокирован, true - заблокирован.
     */
    @Column(name = "locked", nullable = false)
    private boolean locked;

    /**
     * Дата и время окончания срока действия учетной записи пользователя.
     */
    @Column(name = "account_expired")
    private LocalDateTime accountExpired;

    /**
     * Дата и время окончания срока действия пароля пользователя.
     */
    @Column(name = "password_expired")
    private LocalDateTime passwordExpired;

    /**
     * Признак активности пользователя: false - не активен, true - активен.
     */
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
}

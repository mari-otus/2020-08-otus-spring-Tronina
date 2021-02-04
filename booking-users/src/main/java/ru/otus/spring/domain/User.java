package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
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
@Entity
@Table(name = "users")
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
     * ФИО пользователя.
     */
    @Column(name = "fio", nullable = false)
    private String fio;

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

    /**
     * Список ролей пользователя.
     */
    @ManyToMany(targetEntity = UserRole.class, fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(name = "users_role", joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    @OrderBy(value = "id")
    private Set<UserRole> roles;
}

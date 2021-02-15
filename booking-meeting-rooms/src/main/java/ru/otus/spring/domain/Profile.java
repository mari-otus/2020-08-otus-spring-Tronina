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

/**
 * Профиль пользователя.
 *
 * @author MTronina
 */
@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "profiles")
public class Profile {

    /**
     * Идентификатор профиля.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Логин пользователя.
     */
    @Column(name = "login")
    private String login;

    /**
     * Email пользователя.
     */
    @Column(name = "email")
    private String email;

    /**
     * Номер сотового телефона пользователя.
     */
    @Column(name = "mobile_phone")
    private String mobilePhone;

    /**
     * Отправлять уведомления на email.
     */
    @Column(name = "is_email_notify")
    private boolean isEmailNotify;

    /**
     * Отправлять уведомления по sms.
     */
    @Column(name = "is_phone_notify")
    private boolean isPhoneNotify;

}

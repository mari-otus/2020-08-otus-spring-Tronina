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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
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
     * Идентификатор пользователя.
     */
    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Column(name = "is_email_notify")
    private boolean isEmailNotify;

    @Column(name = "is_phone_notify")
    private boolean isPhoneNotify;

}

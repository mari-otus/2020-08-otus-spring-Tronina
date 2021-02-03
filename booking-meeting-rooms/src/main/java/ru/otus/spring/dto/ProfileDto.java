package ru.otus.spring.dto;

import lombok.Data;

/**
 * @author MTronina
 */
@Data
public class ProfileDto {

    private String email;

    private String mobilePhone;

    private boolean isEmailNotify;

    private boolean isPhoneNotify;

}

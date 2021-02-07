package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subscriber {

    private String fio;

    private String email;

    private String mobilePhone;

    private boolean isEmailNotify;

    private boolean isPhoneNotify;

}

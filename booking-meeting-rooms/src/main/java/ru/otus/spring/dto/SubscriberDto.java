package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MTronina
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberDto {

    private String fio;

    private String email;

    private String mobilePhone;

    private boolean isEmailNotify;

    private boolean isPhoneNotify;

}

/*
 * VTB Group. Do not reproduce without permission in writing.
 * Copyright (c) 2020 VTB Group. All rights reserved.
 */

package ru.otus.spring.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Помечает методы, у которых требуется проверить значения параметров mdmId и unc - заголовков HTTP.
 *
 * @author MTronina
 */
@Documented
@Constraint(validatedBy = BookingValidator.class)
@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface BookingConstraint {

    String message() default "beginDate and endDate are not correct!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

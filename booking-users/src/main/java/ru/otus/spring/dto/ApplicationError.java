package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author MTronina
 */
@Data
@AllArgsConstructor
public class ApplicationError {

    private String message;
}

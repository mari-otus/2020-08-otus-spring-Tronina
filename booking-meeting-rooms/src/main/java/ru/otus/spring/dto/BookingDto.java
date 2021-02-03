package ru.otus.spring.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MTronina
 */
@Data
public class BookingDto {

    private Long roomId;

    private LocalDateTime beginDate;

    private LocalDateTime endDate;
}

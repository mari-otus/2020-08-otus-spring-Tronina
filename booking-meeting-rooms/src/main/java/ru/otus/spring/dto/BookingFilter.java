package ru.otus.spring.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author MTronina
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingFilter {

    private String roomName;

    private String login;

    private Period startBooking;

    private Period endBooking;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Period {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime beginPeriod;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
        private LocalDateTime endPeriod;

    }

}

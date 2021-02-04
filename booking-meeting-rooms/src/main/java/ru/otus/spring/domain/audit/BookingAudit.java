package ru.otus.spring.domain.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.otus.spring.domain.Room;
import ru.otus.spring.dto.UserDto;

import java.time.LocalDateTime;

/**
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("bookings")
public class BookingAudit {

    @Id
    private String id;

    @Field(name = "bookingId")
    private Long bookingId;

    @Field(name = "room")
    private Room room;

    @Field(name = "user")
    private UserDto user;

    @Field(name = "beginDate")
    private LocalDateTime beginDate;

    @Field(name = "endDate")
    private LocalDateTime endDate;

    @Field(name = "createDate")
    private LocalDateTime createDate;

    @Field(name = "updateDate")
    private LocalDateTime updateDate;

    @Field(name = "deleteDate")
    private LocalDateTime deleteDate;

}

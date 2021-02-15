package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Бронь.
 *
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {

    /**
     * Идентификатор брони.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Переговорная комната.
     */
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room room;

    /**
     * Логин пользователя, создавшего бронь.
     */
    @Column(name = "login", nullable = false)
    private String login;

    /**
     * Дата и время начала брони.
     */
    @Column(name = "begin_date")
    private LocalDateTime beginDate;

    /**
     * Дата и время окончания брони.
     */
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * Дата и время создания записи.
     */
    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;

    /**
     * Дата и время изменения записи.
     */
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    /**
     * Дата и время удаления записи (удаление брони).
     */
    @Column(name = "delete_date")
    private LocalDateTime deleteDate;
}

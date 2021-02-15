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
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Переговорная комната.
 *
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rooms")
public class Room {

    /**
     * Идентификатор комнаты.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование комнаты.
     */
    @Column(name = "room_name", nullable = false)
    private String roomName;

    /**
     * Вместимость комнаты (кол-во человек).
     */
    @Column(name = "capacity", nullable = false)
    private int capacity;

    /**
     * Наличие кондиционера.
     */
    @Column(name = "has_conditioner")
    private boolean hasConditioner;

    /**
     * Возможность проведения видеоконференций.
     */
    @Column(name = "has_videoconference")
    private boolean hasVideoconference;

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
     * Дата и время удаления записи (удаление переговорной комнаты).
     */
    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

}

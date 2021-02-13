package ru.otus.spring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Room.Filter", description = "Фильтр поиска переговорных комнат")
public class RoomFilter {

    @NotBlank
    @ApiModelProperty(value = "Название переговорной комнаты", required = true)
    private String roomName;

    @NotNull
    @ApiModelProperty(value = "Вместимость комнаты (кол-во человек)", required = true)
    private Integer capacity;

    @ApiModelProperty(value = "Возможность проводить видеоконференции")
    private Boolean hasConditioner;

    @ApiModelProperty(value = "Наличие кондиционера")
    private Boolean hasVideoconference;

    @ApiModelProperty(value = "Наличие подписки")
    private boolean hasSubscribe;

}

package goltsman.bookingtableapp.model.responce.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;

@Builder
public record TableResponse(

        @Schema(description = "ID стола", example = "10")
        Long id,

        @Schema(description = "ID ресторана", example = "1")
        Long restaurantId,

        @Schema(description = "Название ресторана", example = "White Rabbit")
        String restaurantTitle,

        @Schema(description = "Количество мест", example = "4")
        Integer seats,

        @Schema(description = "Описание стола", example = "Стол у окна")
        String description,

        @Schema(description = "Доступен ли стол", example = "true")
        Boolean isAvailable,

        @Schema(description = "Дата создания")
        Instant createdAt
) {
}

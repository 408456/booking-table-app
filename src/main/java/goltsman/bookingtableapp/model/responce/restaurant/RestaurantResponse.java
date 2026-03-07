package goltsman.bookingtableapp.model.responce.restaurant;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Builder
public record RestaurantResponse(

        @Schema(description = "ID ресторана", example = "10")
        Long id,

        @Schema(description = "Название ресторана", example = "White Rabbit")
        String title,

        @Schema(description = "Описание ресторана")
        String description,

        @Schema(description = "Адрес ресторана")
        String address,

        @Schema(description = "Средний чек", example = "2500")
        BigDecimal avgSum,

        @Schema(description = "Ссылка на меню")
        String menu,

        @Schema(description = "Флаг публикации", example = "true")
        Boolean isPublished,

        @ArraySchema(
                schema = @Schema(implementation = CuisineResponse.class),
                arraySchema = @Schema(description = "Список кухонь ресторана"))
        List<CuisineResponse> cuisines,

        @Schema(description = "Дата создания")
        Instant createdAt,

        @Schema(description = "Дата обновления")
        Instant updatedAt
) {
}
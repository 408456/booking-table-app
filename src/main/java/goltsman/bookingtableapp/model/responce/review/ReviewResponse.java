package goltsman.bookingtableapp.model.responce.review;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ReviewResponse(

        @Schema(description = "ID отзыва", example = "10")
        Long id,

        @Schema(description = "ID ресторана", example = "1")
        Long restaurantId,

        @Schema(description = "Название ресторана", example = "White Rabbit")
        String restaurantTitle,

        @Schema(description = "ID пользователя", example = "5")
        Long userId,

        @Schema(description = "Имя пользователя", example = "Иван")
        String userFirstName,

        @Schema(description = "Фамилия пользователя", example = "Иванов")
        String userLastName,

        @Schema(description = "Оценка", example = "5")
        Integer rating,

        @Schema(description = "Текст отзыва", example = "Отличное место!")
        String comment,

        @Schema(description = "Дата создания")
        Instant createdAt
) {
}
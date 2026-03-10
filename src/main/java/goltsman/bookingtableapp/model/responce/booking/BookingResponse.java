package goltsman.bookingtableapp.model.responce.booking;

import goltsman.bookingtableapp.model.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDateTime;

@Builder
public record BookingResponse(

        @Schema(description = "ID брони", example = "10")
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

        @Schema(description = "ID стола", example = "3")
        Long tableId,

        @Schema(description = "Количество мест за столом", example = "4")
        Integer tableSeats,

        @Schema(description = "Время начала бронирования", example = "2025-12-31T19:00:00")
        LocalDateTime startTime,

        @Schema(description = "Время конца бронирования", example = "2025-12-31T19:00:00")
        LocalDateTime endTime,

        @Schema(description = "Количество гостей", example = "4")
        Integer guestsCount,

        @Schema(description = "Статус брони", example = "CONFIRMED")
        BookingStatus status,

        @Schema(description = "Дата создания брони")
        Instant createdAt) {
}
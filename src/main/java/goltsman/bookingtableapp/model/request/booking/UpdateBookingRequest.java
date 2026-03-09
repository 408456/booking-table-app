package goltsman.bookingtableapp.model.request.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import goltsman.bookingtableapp.model.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingRequest {
    @Schema(description = "ID стола", example = "5")
    @Min(value = 1, message = "ID стола должен быть больше 0")
    private Long tableId;

    @Schema(description = "Дата и время бронирования", example = "2025-12-31T20:00:00")
    @Future(message = "Время бронирования должно быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime bookingTime;

    @Schema(description = "Количество гостей", example = "6")
    @Min(value = 1, message = "Количество гостей должно быть больше 0")
    private Integer guestsCount;

    @Schema(description = "Статус брони", example = "CONFIRMED")
    private BookingStatus status;
}

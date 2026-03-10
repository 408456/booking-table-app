package goltsman.bookingtableapp.model.request.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookingRequest {

    @Schema(description = "ID ресторана", example = "1")
    @NotNull(message = "ID ресторана не может быть пустым")
    @Min(value = 1, message = "ID ресторана должен быть больше 0")
    private Long restaurantId;

    @Schema(description = "ID стола", example = "1")
    @NotNull(message = "ID стола не может быть пустым")
    @Min(value = 1, message = "ID стола должен быть больше 0")
    private Long tableId;

    @Schema(description = "Дата и время начала бронирования", example = "2026-03-19T12:00")
    @Future(message = "Время начала должно быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @Schema(description = "Дата и время окончания бронирования", example = "2026-03-19T14:30")
    @Future(message = "Время окончания должно быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    @Schema(description = "Количество гостей", example = "4")
    @NotNull(message = "Количество гостей не может быть пустым")
    @Min(value = 1, message = "Количество гостей должно быть больше 0")
    private Integer guestsCount;
}

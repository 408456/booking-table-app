package goltsman.bookingtableapp.model.request.booking;

import goltsman.bookingtableapp.model.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingStatusRequest {
    @Schema(description = "Новый статус брони", example = "CONFIRMED")
    @NotNull(message = "Статус не может быть пустым")
    private BookingStatus status;
}

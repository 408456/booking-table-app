package goltsman.bookingtableapp.model.responce.booking;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record BookingListResponse(

        @Schema(description = "Общее количество броней", example = "45")
        Integer totalCount,

        @Schema(description = "Номер страницы", example = "1")
        Integer page,

        @Schema(description = "Размер страницы", example = "10")
        Integer pageSize,

        @Schema(description = "Максимальное количество элементов", example = "10")
        Integer limit,

        @ArraySchema(
                schema = @Schema(implementation = BookingResponse.class),
                arraySchema = @Schema(description = "Список броней"))
        List<BookingResponse> bookings
) {
}

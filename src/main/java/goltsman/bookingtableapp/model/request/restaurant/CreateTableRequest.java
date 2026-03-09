package goltsman.bookingtableapp.model.request.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTableRequest {

    @Schema(description = "ID ресторана", example = "1")
    @NotNull(message = "ID ресторана не может быть пустым")
    @Min(value = 1, message = "ID ресторана должен быть больше 0")
    private Long restaurantId;

    @Schema(description = "Количество мест", example = "4")
    @NotNull(message = "Количество мест не может быть пустым")
    @Min(value = 1, message = "Количество мест должно быть больше 0")
    private Integer seats;

    @Schema(description = "Описание стола", example = "Стол у окна")
    @Size(max = 255, message = "Описание не должно превышать 255 символов")
    private String description;

    @Schema(description = "Доступен ли стол для бронирования", example = "true")
    private Boolean isAvailable;
}
package goltsman.bookingtableapp.model.request.restaurant;

import goltsman.bookingtableapp.common.ValidationPatternConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRestaurantRequest {

    @Schema(description = "Название ресторана", example = "White Rabbit")
    @NotBlank
    @Size(max = 255, message = "Название ресторана не должно превышать 255 символов")
    @Pattern(regexp = ValidationPatternConstant.TITLE_PATTERN,
            message = ValidationPatternConstant.TITLE_PATTERN_MESSAGE_ERROR)
    private String title;

    @Schema(description = "Описание ресторана")
    private String description;

    @Schema(description = "Адрес ресторана", example = "Москва, Смоленская площадь 3")
    @NotBlank
    @Size(max = 255, message = "Адрес не должен превышать 255 символов")
    private String address;

    @Schema(description = "Средний чек", example = "2500")
    @DecimalMin(value = "0", message = "Средний чек не может быть отрицательным")
    private BigDecimal avgSum;

    @Schema(description = "Ссылка на PDF меню")
    private String menu;

    @Schema(description = "Флаг публикации ресторана", example = "true")
    private Boolean isPublished;
}
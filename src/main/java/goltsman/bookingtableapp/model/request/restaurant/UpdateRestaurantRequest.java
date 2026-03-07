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
public class UpdateRestaurantRequest {

    @Schema(description = "Название ресторана")
    @Size(max = 255, message = "Название ресторана не должно превышать 255 символов")
    @Pattern(regexp = ValidationPatternConstant.TITLE_PATTERN,
            message = ValidationPatternConstant.TITLE_PATTERN_MESSAGE_ERROR)
    private String title;

    @Schema(description = "Описание ресторана")
    private String description;

    @Schema(description = "Адрес ресторана")
    @Size(max = 255, message = "Адрес не должен превышать 255 символов")
    private String address;

    @Schema(description = "Средний чек")
    @DecimalMin(value = "0", message = "Средний чек не может быть отрицательным")
    private BigDecimal avgSum;

    @Schema(description = "Ссылка на меню")
    private String menu;

    @Schema(description = "Флаг публикации")
    private Boolean isPublished;
}
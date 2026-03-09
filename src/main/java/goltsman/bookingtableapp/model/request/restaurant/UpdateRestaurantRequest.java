package goltsman.bookingtableapp.model.request.restaurant;

import goltsman.bookingtableapp.common.ValidationPatternConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRestaurantRequest {

    @Schema(description = "Название ресторана", example = "White Rabbit")
    @NotBlank
    @Size(max = 255, message = "Название ресторана не должно превышать 255 символов")
    @Pattern(regexp = ValidationPatternConstant.TITLE_PATTERN,
            message = ValidationPatternConstant.TITLE_PATTERN_MESSAGE_ERROR)
    private String title;

    @Schema(description = "Описание ресторана", example = "Закусочная с видом на завод")
    private String description;

    @Schema(description = "Адрес ресторана", example = "Рославль, ул. Урицкого 21 B")
    @NotBlank
    @Size(max = 255, message = "Адрес не должен превышать 255 символов")
    private String address;

    @Schema(description = "Средний чек", example = "2500")
    @DecimalMin(value = "0", message = "Средний чек не может быть отрицательным")
    private BigDecimal avgSum;

    @Schema(description = "Ссылка на PDF меню", example = "http:/example.menu.com/")
    private String menu;

    @Schema(description = "Флаг публикации ресторана", example = "true")
    private Boolean isPublished;

    @Schema(description = "Список id кухонь", example = "[1,  2]")
    private Set<Long> cuisineIds;
}
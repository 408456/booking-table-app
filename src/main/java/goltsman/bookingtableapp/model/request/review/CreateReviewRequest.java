package goltsman.bookingtableapp.model.request.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewRequest {

    @Schema(description = "ID ресторана", example = "1")
    @NotNull(message = "ID ресторана не может быть пустым")
    @Min(value = 1, message = "ID ресторана должен быть больше 0")
    private Long restaurantId;

    @Schema(description = "Оценка ресторана от 1 до 5", example = "5")
    @NotNull(message = "Оценка не может быть пустой")
    @Min(value = 1, message = "Оценка должна быть не меньше 1")
    @Max(value = 5, message = "Оценка должна быть не больше 5")
    private Integer rating;

    @Schema(description = "Текст отзыва", example = "Отличное место!")
    @Size(max = 1000, message = "Комментарий не должен превышать 1000 символов")
    private String comment;
}
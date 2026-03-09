package goltsman.bookingtableapp.model.request.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateReviewRequest {

    @Schema(description = "Оценка ресторана от 1 до 5", example = "4")
    @NotNull(message = "Оценка не может быть пустой")
    @Min(value = 1, message = "Оценка должна быть не меньше 1")
    @Max(value = 5, message = "Оценка должна быть не больше 5")
    private Integer rating;

    @Schema(description = "Текст отзыва", example = "Хорошо, но могло быть лучше")
    @Size(max = 1000, message = "Комментарий не должен превышать 1000 символов")
    private String comment;
}
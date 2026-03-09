package goltsman.bookingtableapp.model.request.restaurant;

import goltsman.bookingtableapp.common.ValidationPatternConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCuisineRequest {

    @Schema(description = "Название кухни", example = "Итальянская")
    @NotBlank
    @Size(max = 100, message = "Название кухни не должно превышать 100 символов")
    @Pattern(regexp = ValidationPatternConstant.TITLE_PATTERN,
            message = ValidationPatternConstant.TITLE_PATTERN_MESSAGE_ERROR)
    private String name;

    @Schema(description = "Описание кухни", example = "Классическая итальянская кухня")
    @Size(max = 100, message = "Описание не должно превышать 100 символов")
    private String description;
}
package goltsman.bookingtableapp.model.request.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCuisineToRestaurantRequest {
    @Schema(description = "ID кухни", example = "3")
    @NotNull
    @Min(value = 1, message = "id кухни должен быть больше 0")
    private Long cuisineId;
}
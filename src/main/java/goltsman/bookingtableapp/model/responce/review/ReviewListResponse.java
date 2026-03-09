package goltsman.bookingtableapp.model.responce.review;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record ReviewListResponse(

        @Schema(description = "Общее количество отзывов", example = "45")
        Integer totalCount,

        @Schema(description = "Номер страницы", example = "1")
        Integer page,

        @Schema(description = "Размер страницы", example = "10")
        Integer pageSize,

        @Schema(description = "Максимальное количество элементов", example = "10")
        Integer limit,

        @ArraySchema(
                schema = @Schema(implementation = ReviewResponse.class),
                arraySchema = @Schema(description = "Список отзывов"))
        List<ReviewResponse> reviews
) {
}
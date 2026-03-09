package goltsman.bookingtableapp.model.responce.restaurant;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record TableListResponse(

        @Schema(description = "Общее количество столов", example = "50")
        Integer totalCount,

        @Schema(description = "Номер страницы", example = "1")
        Integer page,

        @Schema(description = "Размер страницы", example = "10")
        Integer pageSize,

        @Schema(description = "Максимальное количество элементов", example = "10")
        Integer limit,

        @ArraySchema(
                schema = @Schema(implementation = TableResponse.class),
                arraySchema = @Schema(description = "Список столов"))
        List<TableResponse> tables
) {
}

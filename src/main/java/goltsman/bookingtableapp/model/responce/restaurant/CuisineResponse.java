package goltsman.bookingtableapp.model.responce.restaurant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CuisineResponse(

        @Schema(description = "ID кухни", example = "2")
        Long id,

        @Schema(description = "Название кухни", example = "Итальянская")
        String name,

        @Schema(description = "Описание кухни")
        String description,

        @Schema(description = "Дата создания", example = "2026-02-18T15:10:12Z")
        Instant createdAt,

        @Schema(description = "Дата обновления", example = "2026-02-18T15:10:12Z")
        Instant updatedAt
) {
}
package goltsman.bookingtableapp.model.responce.user;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
public record UserListResponse(

        @Schema(description = "Общее количество курсов", example = "150")
        Integer totalCount,

        @Schema(description = "Номер текущей страницы", example = "1")
        Integer page,

        @Schema(description = "Размер страницы (количество элементов на странице)", example = "10")
        Integer pageSize,

        @Schema(description = "Максимальное количество элементов, которое может быть возвращено", example = "100")
        Integer limit,

        @ArraySchema(
                schema = @Schema(implementation = UserResponse.class),
                arraySchema = @Schema(description = "Список пользователей на странице"))
        List<UserResponse> users
) {
}

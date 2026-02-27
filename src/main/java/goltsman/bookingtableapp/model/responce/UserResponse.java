package goltsman.bookingtableapp.model.responce;

import goltsman.bookingtableapp.model.enums.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;

@Builder
public record UserResponse(
        @Schema(description = "Уникальный идентификатор пользователя",
                example = "12")
        Long id,

        @Schema(description = "Роль пользователя",
                example = "ADMIN")
        RoleType role,

        @Schema(description = "Имя пользователя",
                example = "Ринат")
        String firstName,

        @Schema(description = "Фамилия пользователя",
                example = "Госляков")
        String lastName,

        @Schema(description = "Номер телефона пользователя",
                example = "+79939674322")
        String phone,

        @Schema(description = "Адрес электронной почты пользователя",
                example = "mail@mail.ru")
        String email,

        @Schema(description = "Флаг верификации пользователя",
                example = "true")
        Boolean isVerified,

        @Schema(description = "Дата создания пользователя",
                example = "2026-02-18T15:10:12Z")
        Instant createdAt,

        @Schema(description = "Дата последнего обновления пользователя",
                example = "2026-02-18T15:10:12Z")
        Instant updatedAt
) {
}

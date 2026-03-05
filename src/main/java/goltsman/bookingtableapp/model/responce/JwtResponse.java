package goltsman.bookingtableapp.model.responce;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record JwtResponse(
        @Schema(description = "токен авторизации", example = "eyJhbGciOiJ...")
        String token,
        @Schema(description = "токен обновления", example = "eyJhbGciOiJ...")
        String refreshToken
) {
}

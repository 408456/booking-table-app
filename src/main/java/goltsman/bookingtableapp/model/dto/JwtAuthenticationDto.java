package goltsman.bookingtableapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class JwtAuthenticationDto {
    @Schema(description = "токен авторизации", example = "eyJhbGciOiJ...")
    private String token;
    @Schema(description = "токен обноваления", example = "eyJhbGciOiJ...")
    private String refreshToken;
}

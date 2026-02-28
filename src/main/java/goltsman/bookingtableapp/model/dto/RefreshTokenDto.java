package goltsman.bookingtableapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RefreshTokenDto {
    @Schema(description = "токен обновления", example = "eyJhbGciOiJ...")
    private String refreshToken;
}

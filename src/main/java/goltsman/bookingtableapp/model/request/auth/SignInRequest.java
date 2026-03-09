package goltsman.bookingtableapp.model.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRequest {
    @NotBlank
    @Schema(description = "почта пользователя", example = "admin@gmail.com")
    private String email;
    @NotBlank
    @Schema(description = "пароль пользователя", example = "Password123!")
    private String password;
}

package goltsman.bookingtableapp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserCredentialsDto {
    @Schema(description = "почта пользователя", example = "gleb@gmail.com")
    private String email;
    @Schema(description = "пароль пользователя", example = "Pass123!")
    private String password;
}

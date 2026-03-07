package goltsman.bookingtableapp.model.request.auth;

import goltsman.bookingtableapp.common.ValidationPatternConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @Schema(description = "имя пользователя", example = "Обновленный Ринат", minLength = 1, maxLength = 50)
    @Size(min = 1, max = 50, message = "Длина имени пользователя должна быть от 1 до 50 символов")
    @Pattern(regexp = ValidationPatternConstant.TITLE_PATTERN,
            message = ValidationPatternConstant.TITLE_PATTERN_MESSAGE_ERROR)
    private String firstName;

    @Schema(description = "фамилия пользователя", example = "Обновленный Госляков", minLength = 1, maxLength = 50)
    @Size(min = 1, max = 50, message = "Длинна фамилии пользователя должна быть от 1 до 50 символов")
    @Pattern(regexp = ValidationPatternConstant.TITLE_PATTERN,
            message = ValidationPatternConstant.TITLE_PATTERN_MESSAGE_ERROR)
    private String lastName;

    @Schema(description = "номер телефона пользователя", example = "+79939674377", minLength = 10, maxLength = 20)
    @Size(min = 12, message = "Длина телефона должна быть от 12 символов")
    @Pattern(regexp = ValidationPatternConstant.PHONE_PATTERN,
            message = ValidationPatternConstant.PHONE_PATTERN_MESSAGE_ERROR)
    private String phone;

    @Schema(description = "электронная почта пользователя", example = "newuser@example.com", maxLength = 255)
    @Size(max = 255, message = "Длина email не должна превышать 255 символов")
    @Email(regexp = ValidationPatternConstant.EMAIL_PATTERN,
            message = ValidationPatternConstant.EMAIL_PATTERN_MESSAGE_ERROR)
    private String email;

    @Schema(description = "пароль пользователя", example = "Password123!", minLength = 8, maxLength = 255)
    @NotBlank
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @Pattern(regexp = ValidationPatternConstant.PASSWORD_PATTERN,
            message = ValidationPatternConstant.PASSWORD_PATTERN_MESSAGE_ERROR)
    private String password;
}

package goltsman.bookingtableapp.model.request;

import goltsman.bookingtableapp.common.ValidationPatternConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @Schema(description = "старый пароль пользователя", example = "Password123!", minLength = 8, maxLength = 255)
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @Pattern(regexp = ValidationPatternConstant.PASSWORD_PATTERN,
            message = ValidationPatternConstant.PASSWORD_PATTERN_MESSAGE_ERROR)
    private String currentPassword;

    @Schema(description = "новый пароль пользователя", example = "NewPassword123!", minLength = 8, maxLength = 255)
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов")
    @Pattern(regexp = ValidationPatternConstant.PASSWORD_PATTERN,
            message = ValidationPatternConstant.PASSWORD_PATTERN_MESSAGE_ERROR)
    private String newPassword;
}

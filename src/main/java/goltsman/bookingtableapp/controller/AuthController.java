package goltsman.bookingtableapp.controller;

import goltsman.bookingtableapp.model.dto.JwtAuthenticationDto;
import goltsman.bookingtableapp.model.dto.RefreshTokenDto;
import goltsman.bookingtableapp.model.dto.UserCredentialsDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static goltsman.bookingtableapp.common.ApiConstant.BASE_AUTH_CONTROLLER_URL;
import static goltsman.bookingtableapp.common.ApiConstant.REFRESH_URL;
import static goltsman.bookingtableapp.common.ApiConstant.SIGN_IN_URL;

@Validated
@RequestMapping(BASE_AUTH_CONTROLLER_URL)
@Tag(name = "Контроллер аунтификации",
        description = "Этот контроллер позволяет пользователям авторизовываться в системе")

public interface AuthController {

    @PostMapping(SIGN_IN_URL)
    public ResponseEntity<JwtAuthenticationDto> singIn(@Valid @RequestBody UserCredentialsDto userCredentialsDto);

    @PostMapping(REFRESH_URL)
    public JwtAuthenticationDto refresh(@Valid @RequestBody RefreshTokenDto refreshTokenDto);
}

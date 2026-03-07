package goltsman.bookingtableapp.controller;

import goltsman.bookingtableapp.controller.annotation.CommonApiResponses;
import goltsman.bookingtableapp.model.request.auth.RefreshTokenRequest;
import goltsman.bookingtableapp.model.request.auth.SignUpRequest;
import goltsman.bookingtableapp.model.request.auth.SignInRequest;
import goltsman.bookingtableapp.model.responce.auth.JwtResponse;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Контроллер аунтификации", description = "Этот контроллер позволяет пользователям авторизовываться в системе")
public interface AuthController {

    @Operation(
            summary = "Регистрация пользователя",
            description = "Регистрирует пользователя на основе входных данных и возвращает его ответ",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))))
    @CommonApiResponses
    @PostMapping("/sign-up")
    ResponseEntity<MessageResponse> signUp(@Valid @RequestBody SignUpRequest request);

    @Operation(
            summary = "Аунтификация пользователя",
            description = "Аунтифицирует пользователя на основе email и пароля и возвращает его ответ",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))))
    @CommonApiResponses
    @PostMapping(SIGN_IN_URL)
    ResponseEntity<JwtResponse> singIn(@Valid @RequestBody SignInRequest signInRequest);

    @Operation(
            summary = "Создание refresh токен пользователя",
            description = "Создаёт refresh токен пользователя на основе входного запроса и возвращает его ответ",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class))))
    @CommonApiResponses
    @PostMapping(REFRESH_URL)
    ResponseEntity<JwtResponse> refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest);

}

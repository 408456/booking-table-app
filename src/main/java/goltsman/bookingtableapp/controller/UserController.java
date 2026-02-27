package goltsman.bookingtableapp.controllers;

import goltsman.bookingtableapp.common.ApiConstant;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping(ApiConstant.BASE_USER_CONTROLLER_URL)
@Tag(name = "Контроллер пользователей",
        description = "Этот контроллер позволяет выполнять различные операции с пользователями")
public interface UserController {
    @Operation(summary = "Создание пользователя",
            description = "Создаёт пользователя на основе входного запроса и возвращает его данные")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateUserRequest.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                       "code": 400,
                                       "type": "Bad Request",
                                       "message": "Неправильные аргументы запроса"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                      "code": 401,
                                      "type": "Unauthorized",
                                      "message": "Отсутствует или некорректный токен авторизации"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                         "code": 403,
                                         "type": "Forbidden",
                                         "message": "У вас нет прав для выполнения данного действия"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                         "code": 404,
                                         "type": "Not found",
                                         "message": "Запрашиваемый ресурс не найден"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                         "code": 409,
                                         "type": "Conflict",
                                         "message": "Ресурс уже существует"
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = """
                                    {
                                          "code": 500,
                                          "type": "Internal Server Error",
                                          "message": "Неизвестная ошибка сервера. Попробуйте снова"
                                    }
                                    """)
                    )
            )
    })
    @PostMapping
    ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest createUserRequest);
}

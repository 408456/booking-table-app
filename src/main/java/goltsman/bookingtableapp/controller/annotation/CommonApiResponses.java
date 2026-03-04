package goltsman.bookingtableapp.controller.annotation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = {
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
public @interface CommonApiResponses {
}
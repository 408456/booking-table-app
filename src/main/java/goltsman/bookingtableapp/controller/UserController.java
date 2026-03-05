package goltsman.bookingtableapp.controller;

import goltsman.bookingtableapp.controller.annotation.CommonApiResponses;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.request.UpdateUserProfileRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.UserListResponse;
import goltsman.bookingtableapp.model.responce.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static goltsman.bookingtableapp.common.ApiConstant.BASE_USER_CONTROLLER_URL;
import static goltsman.bookingtableapp.common.ApiConstant.ID;
import static goltsman.bookingtableapp.common.ApiConstant.ME_URL;

@Validated
@RequestMapping(BASE_USER_CONTROLLER_URL)
@Tag(name = "Контроллер пользователей",
        description = "Этот контроллер позволяет выполнять различные операции с пользователями")
public interface UserController {

    @Operation(
            summary = "Создание пользователя",
            description = "Создаёт пользователя на основе входного запроса и возвращает его данные",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))))
    @CommonApiResponses
    @PostMapping
    ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest createUserRequest);

    @Operation(
            summary = "Частичное обновление данных пользователя",
            description = "Обновляет имя, фамилию, номер телефона и адрес электронной почты" +
                    " пользователя на основе входного запроса и возвращает обновленные данные",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))))
    @CommonApiResponses
    @PatchMapping(ME_URL)
    ResponseEntity<UserResponse> updateProfile(@Valid @RequestBody UpdateUserProfileRequest updateUserRequest);

    @Operation(
            summary = "Получение пользователя по id",
            description = "Получает пользователя по переданному id и возвращает ответ",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))))
    @CommonApiResponses
    @GetMapping(ID)
    ResponseEntity<UserResponse> getUser(
            @Min(value = 1, message = "id пользователя должен быть целым числом больше 0")
            @PathVariable Long id);

    @Operation(
            summary = "Удаление пользователя по id",
            description = "Удаляет данные пользователя на основе входного id и возвращает ответ",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))))
    @CommonApiResponses
    @DeleteMapping(ID)
    ResponseEntity<MessageResponse> delete(
            @Min(value = 1, message = "id пользователя должен быть целым числом больше 0")
            @PathVariable Long id);

    @Operation(
            summary = "Получение списка пользователей",
            description = "Получает список пользователей и возвращает ответ ответ",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserListResponse.class))))
    @CommonApiResponses
    @GetMapping
    ResponseEntity<UserListResponse> getUsers(
            @Min(value = 1, message = "Номер страницы должен быть больше 0")
            @RequestParam(defaultValue = "1") Integer page,
            @Min(value = 1, message = "Размер страницы должен быть больше 0")
            @Max(value = 100, message = "Размер страницы не должен быть больше 100")
            @RequestParam(defaultValue = "10") Integer pageSize);
}

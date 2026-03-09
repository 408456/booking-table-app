package goltsman.bookingtableapp.controller;

import goltsman.bookingtableapp.controller.annotation.CommonApiResponses;
import goltsman.bookingtableapp.model.request.restaurant.CreateTableRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateTableRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.TableListResponse;
import goltsman.bookingtableapp.model.responce.restaurant.TableResponse;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static goltsman.bookingtableapp.common.ApiConstant.ID;
import static goltsman.bookingtableapp.common.ApiConstant.TABLE_CONTROLLER_URL;

@Validated
@RequestMapping(TABLE_CONTROLLER_URL)
@Tag(name = "Контроллер столов",
        description = "Позволяет выполнять операции со столами в ресторанах")
public interface TableController {

    @Operation(
            summary = "Создание стола",
            description = "Создает стол для ресторана",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TableResponse.class))))
    @CommonApiResponses
    @PostMapping
    ResponseEntity<TableResponse> create(@Valid @RequestBody CreateTableRequest request);

    @Operation(
            summary = "Получение стола по id",
            description = "Возвращает детальную информацию о столе",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TableResponse.class))))
    @CommonApiResponses
    @GetMapping(ID)
    ResponseEntity<TableResponse> getTable(
            @Min(value = 1, message = "id стола должен быть больше 0")
            @PathVariable Long id);

    @Operation(
            summary = "Обновление стола",
            description = "Обновляет данные стола",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TableResponse.class))))
    @CommonApiResponses
    @PutMapping(ID)
    ResponseEntity<TableResponse> update(
            @Min(value = 1, message = "id стола должен быть больше 0")
            @PathVariable Long id,
            @Valid @RequestBody UpdateTableRequest request);

    @Operation(
            summary = "Удаление стола",
            description = "Удаляет стол",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))))
    @CommonApiResponses
    @DeleteMapping(ID)
    ResponseEntity<MessageResponse> delete(
            @Min(value = 1, message = "id стола должен быть больше 0")
            @PathVariable Long id);

    @Operation(
            summary = "Получение списка столов",
            description = "Список столов с фильтрацией, пагинацией и сортировкой",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TableListResponse.class))))
    @CommonApiResponses
    @GetMapping
    ResponseEntity<TableListResponse> getTables(
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(required = false) Integer minSeats,
            @RequestParam(required = false) Integer maxSeats,
            @RequestParam(required = false) Boolean isAvailable,
            @Min(value = 1, message = "Номер страницы должен быть больше 0")
            @RequestParam(defaultValue = "1") Integer page,
            @Min(value = 1, message = "Размер страницы должен быть больше 0")
            @Max(value = 100, message = "Размер страницы не должен быть больше 100")
            @RequestParam(defaultValue = "10") Integer pageSize
    );
}

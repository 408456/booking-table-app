package goltsman.bookingtableapp.controller;

import goltsman.bookingtableapp.controller.annotation.CommonApiResponses;
import goltsman.bookingtableapp.model.request.restaurant.CreateCuisineRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateCuisineRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.CuisineResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

import java.util.List;

import static goltsman.bookingtableapp.common.ApiConstant.CUISINE_CONTROLLER_URL;
import static goltsman.bookingtableapp.common.ApiConstant.ID;

@Validated
@RequestMapping(CUISINE_CONTROLLER_URL)
@Tag(name = "Контроллер кухонь",
        description = "Этот контроллер позволяет выполнять различные операции с кухонями")
public interface CuisineController {

    @Operation(
            summary = "Создание кухни",
            description = "Создает новую кухню",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CuisineResponse.class))))
    @CommonApiResponses
    @PostMapping
    ResponseEntity<CuisineResponse> create(
            @Valid @RequestBody CreateCuisineRequest request
    );

    @Operation(
            summary = "Получение кухни",
            description = "Возвращает кухню по id",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CuisineResponse.class))))
    @CommonApiResponses
    @GetMapping(ID)
    ResponseEntity<CuisineResponse> getCuisine(
            @Min(value = 1, message = "id кухни должен быть больше 0")
            @PathVariable Long id
    );

    @Operation(
            summary = "Получение списка кухонь",
            description = "Возвращает список всех кухонь")
    @CommonApiResponses
    @GetMapping
    ResponseEntity<List<CuisineResponse>> getCuisines();

    @Operation(
            summary = "Обновление кухни",
            description = "Обновляет данные кухни",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CuisineResponse.class))))
    @CommonApiResponses
    @PutMapping(ID)
    ResponseEntity<CuisineResponse> update(
            @Min(value = 1, message = "id кухни должен быть больше 0")
            @PathVariable Long id,
            @Valid @RequestBody UpdateCuisineRequest request
    );

    @Operation(
            summary = "Удаление кухни",
            description = "Удаляет кухню",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))))
    @CommonApiResponses
    @DeleteMapping(ID)
    ResponseEntity<MessageResponse> delete(
            @Min(value = 1, message = "id кухни должен быть больше 0")
            @PathVariable Long id
    );
}
package goltsman.bookingtableapp.controller;

import goltsman.bookingtableapp.controller.annotation.CommonApiResponses;
import goltsman.bookingtableapp.model.request.cuisine.CreateCuisineRequest;
import goltsman.bookingtableapp.model.request.cuisine.UpdateCuisineRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.CuisineResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantListResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static goltsman.bookingtableapp.common.ApiConstant.BASE_CUISINE_CONTROLLER_URL;
import static goltsman.bookingtableapp.common.ApiConstant.ID;

@Validated
@RequestMapping(BASE_CUISINE_CONTROLLER_URL)
@Tag(name = "Контроллер типа кухни",
        description = "Позволяет выполнять операции с типами кухонь")
public interface CuisineController {

    @Operation(
            summary = "Создание типа кухни",
            description = "Создает тип кухни по заданному запросу и возвращает CuisineResponse",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CuisineResponse.class))))
    @CommonApiResponses
    @PostMapping
    ResponseEntity<CuisineResponse> create(CreateCuisineRequest request);

    @Operation(
            summary = "Обновление типа кухни",
            description = "Полностью данные типа кухни",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CuisineResponse.class))))
    @CommonApiResponses
    @PutMapping(ID)
    ResponseEntity<CuisineResponse> update(Long id, UpdateCuisineRequest request);

    @Operation(
            summary = "Удаление типа кухни",
            description = "Удаляет тип кухни по id и возвращает ответ",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))))
    @CommonApiResponses
    @DeleteMapping(ID)
    ResponseEntity<MessageResponse> delete(Long id);
}

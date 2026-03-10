package goltsman.bookingtableapp.controller;

import goltsman.bookingtableapp.controller.annotation.CommonApiResponses;
import goltsman.bookingtableapp.model.request.restaurant.CreateRestaurantRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateRestaurantRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantListResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantResponse;
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

import java.math.BigDecimal;
import java.util.List;

import static goltsman.bookingtableapp.common.ApiConstant.ID;
import static goltsman.bookingtableapp.common.ApiConstant.RESTAURANT_BY_CUISINE;
import static goltsman.bookingtableapp.common.ApiConstant.RESTAURANT_CONTROLLER_URL;


@Validated
@RequestMapping(RESTAURANT_CONTROLLER_URL)
@Tag(name = "Контроллер ресторанов",
        description = "Позволяет выполнять операции с ресторанами")
public interface RestaurantController {

    @Operation(
            summary = "Создание ресторана",
            description = "Создает ресторан",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponse.class))))
    @CommonApiResponses
    @PostMapping
    ResponseEntity<RestaurantResponse> create(
            @Valid @RequestBody CreateRestaurantRequest request);


    @Operation(
            summary = "Получение ресторана по id",
            description = "Возвращает детальную информацию о ресторане",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponse.class))))
    @CommonApiResponses
    @GetMapping(ID)
    ResponseEntity<RestaurantResponse> getRestaurant(
            @Min(value = 1, message = "id ресторана должен быть больше 0")
            @PathVariable Long id);


    @Operation(
            summary = "Обновление ресторана",
            description = "Обновляет данные ресторана",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantResponse.class))))
    @CommonApiResponses
    @PutMapping(ID)
    ResponseEntity<RestaurantResponse> update(
            @Min(value = 1, message = "id ресторана должен быть больше 0")
            @PathVariable Long id,
            @Valid @RequestBody UpdateRestaurantRequest request);


    @Operation(
            summary = "Удаление ресторана",
            description = "Удаляет ресторан",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))))
    @CommonApiResponses
    @DeleteMapping(ID)
    ResponseEntity<MessageResponse> delete(
            @Min(value = 1, message = "id ресторана должен быть больше 0")
            @PathVariable Long id);


    @Operation(
            summary = "Получение списка ресторанов",
            description = "Список ресторанов с фильтрацией и пагинацией",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantListResponse.class))))
    @CommonApiResponses
    @GetMapping
    ResponseEntity<RestaurantListResponse> getRestaurants(

            @RequestParam(required = false) String title,

            @RequestParam(required = false) Long cuisineId,

            @RequestParam(required = false) String address,

            @RequestParam(required = false) BigDecimal minAvgSum,

            @RequestParam(required = false) BigDecimal maxAvgSum,

            @RequestParam(required = false) Boolean isPublished,

            @Min(value = 1, message = "Номер страницы должен быть больше 0")
            @RequestParam(defaultValue = "1") Integer page,

            @Min(value = 1, message = "Размер страницы должен быть больше 0")
            @Max(value = 100, message = "Размер страницы не должен быть больше 100")
            @RequestParam(defaultValue = "10") Integer pageSize
    );

    @Operation(
            summary = "Получение ресторанов по id кухни",
            description = "Возвращает список всех ресторанов, имеющих указанную кухню (без пагинации)",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantListResponse.class))))
    @CommonApiResponses
    @GetMapping(RESTAURANT_BY_CUISINE)
    ResponseEntity<List<RestaurantResponse>> getRestaurantsByCuisine(
            @Min(value = 1, message = "id кухни должен быть больше 0")
            @PathVariable Long cuisineId);
}
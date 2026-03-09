package goltsman.bookingtableapp.controller;

import goltsman.bookingtableapp.controller.annotation.CommonApiResponses;
import goltsman.bookingtableapp.model.request.review.CreateReviewRequest;
import goltsman.bookingtableapp.model.request.review.UpdateReviewRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.review.ReviewListResponse;
import goltsman.bookingtableapp.model.responce.review.ReviewResponse;
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
import static goltsman.bookingtableapp.common.ApiConstant.REVIEW_CONTROLLER_URL;

@Validated
@RequestMapping(REVIEW_CONTROLLER_URL)
@Tag(name = "Контроллер отзывов",
        description = "Позволяет выполнять операции с отзывами")
public interface ReviewController {

    @Operation(
            summary = "Создание отзыва",
            description = "Создает отзыв на ресторан от имени текущего пользователя",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponse.class))))
    @CommonApiResponses
    @PostMapping
    ResponseEntity<ReviewResponse> create(@Valid @RequestBody CreateReviewRequest request);

    @Operation(
            summary = "Получение отзыва по id",
            description = "Возвращает детальную информацию об отзыве",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponse.class))))
    @CommonApiResponses
    @GetMapping(ID)
    ResponseEntity<ReviewResponse> getReview(
            @Min(value = 1, message = "id отзыва должен быть больше 0")
            @PathVariable Long id);

    @Operation(
            summary = "Обновление отзыва",
            description = "Обновляет данные отзыва (только автор или админ)",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponse.class))))
    @CommonApiResponses
    @PutMapping(ID)
    ResponseEntity<ReviewResponse> update(
            @Min(value = 1, message = "id отзыва должен быть больше 0")
            @PathVariable Long id,
            @Valid @RequestBody UpdateReviewRequest request);

    @Operation(
            summary = "Удаление отзыва",
            description = "Удаляет отзыв (только автор или админ)",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))))
    @CommonApiResponses
    @DeleteMapping(ID)
    ResponseEntity<MessageResponse> delete(
            @Min(value = 1, message = "id отзыва должен быть больше 0")
            @PathVariable Long id);

    @Operation(
            summary = "Получение списка отзывов",
            description = "Список отзывов с фильтрацией, пагинацией и сортировкой",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewListResponse.class))))
    @CommonApiResponses
    @GetMapping
    ResponseEntity<ReviewListResponse> getReviews(
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) Integer minRating,
            @RequestParam(required = false) Integer maxRating,
            @Min(value = 1, message = "Номер страницы должен быть больше 0")
            @RequestParam(defaultValue = "1") Integer page,
            @Min(value = 1, message = "Размер страницы должен быть больше 0")
            @Max(value = 100, message = "Размер страницы не должен быть больше 100")
            @RequestParam(defaultValue = "10") Integer pageSize
    );
}
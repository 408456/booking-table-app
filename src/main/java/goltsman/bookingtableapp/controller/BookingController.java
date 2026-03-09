package goltsman.bookingtableapp.controller;


import goltsman.bookingtableapp.controller.annotation.CommonApiResponses;
import goltsman.bookingtableapp.model.request.booking.CreateBookingRequest;
import goltsman.bookingtableapp.model.request.booking.UpdateBookingRequest;
import goltsman.bookingtableapp.model.request.booking.UpdateBookingStatusRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.booking.BookingListResponse;
import goltsman.bookingtableapp.model.responce.booking.BookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

import static goltsman.bookingtableapp.common.ApiConstant.BOOKING_CONTROLLER_URL;
import static goltsman.bookingtableapp.common.ApiConstant.ID;

@Validated
@RequestMapping(BOOKING_CONTROLLER_URL)
@Tag(name = "Контроллер бронирований",
        description = "Позволяет выполнять операции с бронированиями столов")
public interface BookingController {

    @Operation(
            summary = "Создание брони",
            description = "Создает бронь стола от имени текущего пользователя",
            responses = @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class))))
    @CommonApiResponses
    @PostMapping
    ResponseEntity<BookingResponse> create(@Valid @RequestBody CreateBookingRequest request);

    @Operation(
            summary = "Получение брони по id",
            description = "Возвращает детальную информацию о брони (доступно автору или админу)",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class))))
    @CommonApiResponses
    @GetMapping(ID)
    ResponseEntity<BookingResponse> getBooking(
            @Min(value = 1, message = "id брони должен быть больше 0")
            @PathVariable Long id);

    @Operation(
            summary = "Обновление брони",
            description = "Обновляет данные брони (доступно автору для статусов CREATED/CONFIRMED или админу)",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class))))
    @CommonApiResponses
    @PutMapping(ID)
    ResponseEntity<BookingResponse> update(
            @Min(value = 1, message = "id брони должен быть больше 0")
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingRequest request);

    @Operation(
            summary = "Обновление статуса брони",
            description = "Изменяет статус брони (пользователь может только отменить свою, админ - любой)",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookingResponse.class))))
    @CommonApiResponses
    @PatchMapping(ID + "/status")
    ResponseEntity<BookingResponse> updateStatus(
            @Min(value = 1, message = "id брони должен быть больше 0")
            @PathVariable Long id,
            @Valid @RequestBody UpdateBookingStatusRequest request);

    @Operation(
            summary = "Удаление брони",
            description = "Удаляет бронь (только для администратора)",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class))))
    @CommonApiResponses
    @DeleteMapping(ID)
    ResponseEntity<MessageResponse> delete(
            @Min(value = 1, message = "id брони должен быть больше 0")
            @PathVariable Long id);

    @Operation(
            summary = "Получение списка броней",
            description = "Список броней с фильтрацией, пагинацией и сортировкой. " +
                    "Для обычного пользователя доступны только его брони.",
            responses = @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookingListResponse.class))))
    @CommonApiResponses
    @GetMapping
    ResponseEntity<BookingListResponse> getBookings(
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long tableId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime bookingTimeFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime bookingTimeTo,
            @Min(value = 1, message = "Номер страницы должен быть больше 0")
            @RequestParam(defaultValue = "1") Integer page,
            @Min(value = 1, message = "Размер страницы должен быть больше 0")
            @Max(value = 100, message = "Размер страницы не должен быть больше 100")
            @RequestParam(defaultValue = "10") Integer pageSize
    );
}
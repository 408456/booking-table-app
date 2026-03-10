package goltsman.bookingtableapp.controller.impl;

import goltsman.bookingtableapp.controller.BookingController;
import goltsman.bookingtableapp.model.request.booking.CreateBookingRequest;
import goltsman.bookingtableapp.model.request.booking.UpdateBookingRequest;
import goltsman.bookingtableapp.model.request.booking.UpdateBookingStatusRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.booking.BookingListResponse;
import goltsman.bookingtableapp.model.responce.booking.BookingResponse;
import goltsman.bookingtableapp.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookingControllerImpl implements BookingController {
    private final BookingService bookingService;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingResponse> create(CreateBookingRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookingService.create(request));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingResponse> getBooking(Long id) {
        return ResponseEntity.ok(bookingService.getBooking(id));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingResponse> update(Long id, UpdateBookingRequest request) {
        return ResponseEntity.ok(bookingService.update(id, request));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingResponse> updateStatus(Long id, UpdateBookingStatusRequest request) {
        return ResponseEntity.ok(bookingService.updateStatus(id, request));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> delete(Long id) {
        return ResponseEntity.ok(bookingService.delete(id));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BookingListResponse> getBookings(
            Long restaurantId,
            Long userId,
            Long tableId,
            String status,
            LocalDateTime bookingTimeFrom,
            LocalDateTime bookingTimeTo,
            Integer page,
            Integer pageSize) {

        PageRequest pageable = PageRequest.of(Math.max(page - 1, 0), Math.max(pageSize, 1));
        return ResponseEntity.ok(bookingService.getBookings(
                restaurantId,
                userId,
                tableId,
                status,
                bookingTimeFrom,
                bookingTimeTo,
                pageable));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponse>> getBookingsByRestaurant(Long restaurantId) {
        return ResponseEntity.ok(bookingService.getBookingsByRestaurant(restaurantId));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(Long userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BookingResponse>> getMyBookings() {
        return ResponseEntity.ok(bookingService.getMyBookings());
    }
}

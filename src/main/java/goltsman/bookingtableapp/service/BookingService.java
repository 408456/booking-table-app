package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.request.booking.CreateBookingRequest;
import goltsman.bookingtableapp.model.request.booking.UpdateBookingRequest;
import goltsman.bookingtableapp.model.request.booking.UpdateBookingStatusRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.booking.BookingListResponse;
import goltsman.bookingtableapp.model.responce.booking.BookingResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    BookingResponse create(CreateBookingRequest request);

    BookingResponse update(Long id, UpdateBookingRequest request);

    BookingResponse updateStatus(Long id, UpdateBookingStatusRequest request);

    MessageResponse delete(Long id);

    BookingResponse getBooking(Long id);

    BookingListResponse getBookings(
            Long restaurantId,
            Long userId,
            Long tableId,
            String status,
            LocalDateTime bookingTimeFrom,
            LocalDateTime bookingTimeTo,
            Pageable pageable
    );

    List<BookingResponse> getBookingsByRestaurant(Long restaurantId);
    List<BookingResponse> getBookingsByUser(Long userId);
    List<BookingResponse> getMyBookings();
}

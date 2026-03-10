package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.entity.Booking;
import goltsman.bookingtableapp.model.entity.Restaurant;
import goltsman.bookingtableapp.model.entity.TableEntity;
import goltsman.bookingtableapp.model.enums.BookingStatus;

import java.time.LocalDateTime;

public interface BookingValidationService {

    Restaurant validateRestaurantExists(Long restaurantId);

    TableEntity validateTableExistsAndAvailable(Long tableId, Long restaurantId);

    void validateBookingInterval(LocalDateTime startTime, LocalDateTime endTime);

    void validateNoTimeConflict(Long tableId, LocalDateTime startTime, LocalDateTime endTime, Long excludeBookingId);

    void validateBookingOwnership(Booking booking, Long userId, boolean isAdmin);

    void validateBookingUpdatable(Booking booking);

    void validateBookingStatusTransition(Booking booking, BookingStatus newStatus, Long userId, boolean isAdmin);
}
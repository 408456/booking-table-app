package goltsman.bookingtableapp.service.impl;

import goltsman.bookingtableapp.exception.BookingConflictException;
import goltsman.bookingtableapp.model.entity.Booking;
import goltsman.bookingtableapp.model.entity.Restaurant;
import goltsman.bookingtableapp.model.entity.TableEntity;
import goltsman.bookingtableapp.model.enums.BookingStatus;
import goltsman.bookingtableapp.repository.BookingRepository;
import goltsman.bookingtableapp.repository.RestaurantRepository;
import goltsman.bookingtableapp.repository.TableRepository;
import goltsman.bookingtableapp.service.BookingValidationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingValidationServiceImpl implements BookingValidationService {

    private final RestaurantRepository restaurantRepository;
    private final TableRepository tableRepository;
    private final BookingRepository bookingRepository;

    @Override
    public void validateBookingInterval(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Время начала и окончания должны быть указаны");
        }
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("Время окончания должно быть позже времени начала");
        }
        long hours = Duration.between(startTime, endTime).toHours();
        long minutes = Duration.between(startTime, endTime).toMinutes();
        if (hours > 3) {
            throw new IllegalArgumentException("Продолжительность бронирования не может превышать 3 часов");
        }
        if (minutes < 30) {
            throw new IllegalArgumentException("Минимальная продолжительность бронирования - 1 минута");
        }
    }

    @Override
    public Restaurant validateRestaurantExists(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Ресторан с id " + restaurantId + " не найден"));
    }

    @Override
    public TableEntity validateTableExistsAndAvailable(Long tableId, Long restaurantId) {
        TableEntity table = tableRepository.findById(tableId)
                .orElseThrow(() -> new EntityNotFoundException("Стол с id " + tableId + " не найден"));

        if (!table.getRestaurant().getId().equals(restaurantId)) {
            throw new IllegalArgumentException("Стол не принадлежит указанному ресторану");
        }

        if (!table.getIsAvailable()) {
            throw new IllegalStateException("Стол недоступен для бронирования");
        }

        return table;
    }

    @Override
    public void validateNoTimeConflict(Long tableId,
                                       LocalDateTime startTime,
                                       LocalDateTime endTime,
                                       Long excludeBookingId
    ) {
        List<Booking> conflicts = bookingRepository.findConflictingBookings(tableId, startTime, endTime)
                .stream()
                .filter(b -> !b.getId().equals(excludeBookingId))
                .toList();
        if (!conflicts.isEmpty()) {
            throw new BookingConflictException("Стол уже забронирован на указанное время");
        }
    }

    @Override
    public void validateBookingOwnership(Booking booking, Long userId, boolean isAdmin) {
        if (!isAdmin && !booking.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("У вас нет прав на выполнение этой операции с данной бронью");
        }
    }

    @Override
    public void validateBookingUpdatable(Booking booking) {
        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Нельзя обновить отмененную или завершенную бронь");
        }
    }

    @Override
    public void validateBookingStatusTransition(Booking booking, BookingStatus newStatus, Long userId, boolean isAdmin) {
        if (!isAdmin && !booking.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("У вас нет прав на изменение статуса этой брони");
        }

        if (!isAdmin) {
            if (newStatus != BookingStatus.CANCELLED) {
                throw new AccessDeniedException("Вы можете только отменить свою бронь");
            }
            if (booking.getStatus() == BookingStatus.COMPLETED) {
                throw new IllegalStateException("Нельзя отменить завершенную бронь");
            }
        }
    }
}

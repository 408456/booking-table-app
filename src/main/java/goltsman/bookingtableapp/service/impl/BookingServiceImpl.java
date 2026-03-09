package goltsman.bookingtableapp.service.impl;


import goltsman.bookingtableapp.exception.BookingConflictException;
import goltsman.bookingtableapp.mapper.BookingMapper;
import goltsman.bookingtableapp.model.entity.Booking;
import goltsman.bookingtableapp.model.entity.Restaurant;
import goltsman.bookingtableapp.model.entity.TableEntity;
import goltsman.bookingtableapp.model.entity.User;
import goltsman.bookingtableapp.model.enums.BookingStatus;
import goltsman.bookingtableapp.model.request.booking.CreateBookingRequest;
import goltsman.bookingtableapp.model.request.booking.UpdateBookingRequest;
import goltsman.bookingtableapp.model.request.booking.UpdateBookingStatusRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.booking.BookingListResponse;
import goltsman.bookingtableapp.model.responce.booking.BookingResponse;
import goltsman.bookingtableapp.repository.BookingRepository;
import goltsman.bookingtableapp.repository.RestaurantRepository;
import goltsman.bookingtableapp.repository.TableRepository;
import goltsman.bookingtableapp.repository.specification.BookingSpecification;
import goltsman.bookingtableapp.security.SecurityService;
import goltsman.bookingtableapp.service.BookingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RestaurantRepository restaurantRepository;
    private final TableRepository tableRepository;
    private final SecurityService securityService;

    @Override
    @Transactional
    public BookingResponse create(CreateBookingRequest request) {
        log.info("Попытка создать бронь для стола {} в ресторане {} на время {}",
                request.getTableId(), request.getRestaurantId(), request.getBookingTime());
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Ресторан с id " + request.getRestaurantId() + " не найден"));
        TableEntity table = tableRepository.findById(request.getTableId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Стол с id " + request.getTableId() + " не найден"));
        if (!table.getRestaurant().getId().equals(restaurant.getId())) {
            throw new IllegalArgumentException("Стол не принадлежит указанному ресторану");
        }
        if (!table.getIsAvailable()) {
            throw new IllegalStateException("Стол недоступен для бронирования");
        }
        if (!bookingRepository.findActiveBookingsByTableAndTime(table.getId(), request.getBookingTime()).isEmpty()) {
            throw new BookingConflictException("Стол уже забронирован на указанное время");
        }
        User currentUser = securityService.getCurrentUser();
        Booking booking = bookingMapper.toEntity(request);
        booking.setRestaurant(restaurant);
        booking.setTable(table);
        booking.setUser(currentUser);
        booking.setStatus(BookingStatus.CREATED);
        bookingRepository.save(booking);
        log.info("Бронь успешно создана с id {}", booking.getId());
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional
    public BookingResponse update(Long id, UpdateBookingRequest request) {
        log.info("Попытка обновить бронь с id {}", id);

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Бронь с id " + id + " не найден"));

        User currentUser = securityService.getCurrentUser();
        if (!currentUser.getId().equals(booking.getUser().getId()) && !securityService.isAdmin()) {
            throw new AccessDeniedException("У вас нет прав на обновление этой брони");
        }
        if (booking.getStatus() == BookingStatus.CANCELLED || booking.getStatus() == BookingStatus.COMPLETED) {
            throw new IllegalStateException("Нельзя обновить отмененную или завершенную бронь");
        }
        if (request.getTableId() != null || request.getBookingTime() != null) {
            Long tableId = request.getTableId() != null ? request.getTableId() : booking.getTable().getId();
            LocalDateTime newTime =
                    request.getBookingTime() != null ? request.getBookingTime() : booking.getBookingTime();
            TableEntity table;
            if (request.getTableId() != null) {
                table = tableRepository.findById(request.getTableId()).orElseThrow(() ->
                        new EntityNotFoundException("Стол с id " + request.getTableId() + " не найден"));
                if (!table.getIsAvailable()) {
                    throw new IllegalStateException("Стол недоступен для бронирования");
                }
            } else {
                table = booking.getTable();
            }
            var conflicting = bookingRepository.findActiveBookingsByTableAndTime(tableId, newTime).stream()
                    .filter(b -> !b.getId().equals(booking.getId()))
                    .toList();
            if (!conflicting.isEmpty()) {
                throw new BookingConflictException("Стол уже забронирован на указанное время");
            }
            if (request.getTableId() != null) {
                booking.setTable(table);
            }
        }
        if (request.getGuestsCount() != null) {
            booking.setGuestsCount(request.getGuestsCount());
        }
        if (request.getBookingTime() != null) {
            booking.setBookingTime(request.getBookingTime());
        }
        if (request.getStatus() != null && securityService.isAdmin()) {
            booking.setStatus(request.getStatus());
        } else if (request.getStatus() != null) {
            throw new AccessDeniedException("Только администратор может изменять статус брони");
        }

        bookingRepository.save(booking);
        log.info("Бронь с id {} успешно обновлена", id);

        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional
    public BookingResponse updateStatus(Long id, UpdateBookingStatusRequest request) {
        log.info("Попытка обновить статус брони с id {} на {}", id, request.getStatus());

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Бронь с id " + id + " не найден"));

        User currentUser = securityService.getCurrentUser();
        if (currentUser.getId().equals(booking.getUser().getId()) && request.getStatus() == BookingStatus.CANCELLED) {
            if (booking.getStatus() == BookingStatus.COMPLETED) {
                throw new IllegalStateException("Нельзя отменить завершенную бронь");
            }
            booking.setStatus(BookingStatus.CANCELLED);
        }
        else if (securityService.isAdmin()) {
            booking.setStatus(request.getStatus());
        } else {
            throw new AccessDeniedException("У вас нет прав на изменение статуса этой брони");
        }
        bookingRepository.save(booking);
        log.info("Статус брони с id {} успешно обновлен на {}", id, request.getStatus());

        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional
    public MessageResponse delete(Long id) {
        log.info("Попытка удалить бронь с id {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Бронь с id " + id + " не найден"));
        if (!securityService.isAdmin()) {
            throw new AccessDeniedException("Только администратор может удалить бронь");
        }
        bookingRepository.delete(booking);
        log.info("Бронь с id {} успешно удалена", id);

        return MessageResponse.builder().message("Бронь успешно удалена").build();
    }

    @Override
    @Transactional(readOnly = true)
    public BookingResponse getBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Бронь с id " + id + " не найдена"));
        User currentUser = securityService.getCurrentUser();
        if (!currentUser.getId().equals(booking.getUser().getId()) && !securityService.isAdmin()) {
            throw new AccessDeniedException("У вас нет прав на просмотр этой брони");
        }

        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingListResponse getBookings(
            Long restaurantId,
            Long userId,
            Long tableId,
            String status,
            LocalDateTime bookingTimeFrom,
            LocalDateTime bookingTimeTo,
            Pageable pageable) {

        BookingStatus statusEnum = null;
        if (status != null) {
            try {
                statusEnum = BookingStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Некорректный статус: " + status);
            }
        }

        User currentUser = securityService.getCurrentUser();
        Long effectiveUserId = userId;
        if (!securityService.isAdmin()) {
            if (userId != null && !userId.equals(currentUser.getId())) {
                throw new AccessDeniedException("Вы можете просматривать только свои брони");
            }
            effectiveUserId = currentUser.getId();
        }

        Specification<Booking> specification = Specification
                .where(BookingSpecification.byRestaurantId(restaurantId))
                .and(BookingSpecification.byUserId(effectiveUserId))
                .and(BookingSpecification.byTableId(tableId))
                .and(BookingSpecification.byStatus(statusEnum))
                .and(BookingSpecification.byBookingTimeFrom(bookingTimeFrom))
                .and(BookingSpecification.byBookingTimeTo(bookingTimeTo));

        Page<Booking> page = bookingRepository.findAll(specification, pageable);
        var bookings = page.getContent().stream()
                .map(bookingMapper::toResponse)
                .toList();

        return BookingListResponse.builder()
                .totalCount((int) page.getTotalElements())
                .page(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .limit(pageable.getPageSize())
                .bookings(bookings)
                .build();
    }
}


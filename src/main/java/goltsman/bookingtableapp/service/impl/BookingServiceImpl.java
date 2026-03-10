package goltsman.bookingtableapp.service.impl;

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
import goltsman.bookingtableapp.repository.TableRepository;
import goltsman.bookingtableapp.repository.specification.BookingSpecification;
import goltsman.bookingtableapp.security.SecurityService;
import goltsman.bookingtableapp.service.BookingService;
import goltsman.bookingtableapp.service.BookingValidationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final TableRepository tableRepository;
    private final BookingMapper bookingMapper;
    private final SecurityService securityService;
    private final BookingValidationService bookingValidationService;

    @Override
    @Transactional
    public BookingResponse create(CreateBookingRequest request) {
        log.info("Попытка создать бронь для стола {} в ресторане {} с {} по {}",
                request.getTableId(), request.getRestaurantId(), request.getStartTime(), request.getEndTime());

        bookingValidationService.validateBookingInterval(request.getStartTime(), request.getEndTime());
        Restaurant restaurant = bookingValidationService.validateRestaurantExists(request.getRestaurantId());
        TableEntity table = bookingValidationService.validateTableExistsAndAvailable(
                request.getTableId(), request.getRestaurantId());
        bookingValidationService.validateNoTimeConflict(table.getId(), request.getStartTime(), request.getEndTime(), null);

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
                .orElseThrow(() -> new EntityNotFoundException("Бронь с id " + id + " не найдена"));

        User currentUser = securityService.getCurrentUser();
        boolean isAdmin = securityService.isAdmin();

        bookingValidationService.validateBookingOwnership(booking, currentUser.getId(), isAdmin);
        bookingValidationService.validateBookingUpdatable(booking);

        if (hasTimeOrTableChanges(request)) {
            Long newTableId = request.getTableId() != null ?
                    request.getTableId() : booking.getTable().getId();
            LocalDateTime newStartTime = request.getStartTime() != null ?
                    request.getStartTime() : booking.getStartTime();
            LocalDateTime newEndTime = request.getEndTime() != null ?
                    request.getEndTime() : booking.getEndTime();

            bookingValidationService.validateBookingInterval(newStartTime, newEndTime);

            if (request.getTableId() != null) {
                TableEntity newTable = bookingValidationService.validateTableExistsAndAvailable(
                        request.getTableId(), booking.getRestaurant().getId());
                booking.setTable(newTable);
            }

            bookingValidationService.validateNoTimeConflict(newTableId, newStartTime, newEndTime, booking.getId());
        }

        if (request.getGuestsCount() != null) booking.setGuestsCount(request.getGuestsCount());
        if (request.getStartTime() != null) booking.setStartTime(request.getStartTime());
        if (request.getEndTime() != null) booking.setEndTime(request.getEndTime());

        bookingRepository.save(booking);
        log.info("Бронь с id {} успешно обновлена", id);

        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional
    public BookingResponse updateStatus(Long id, UpdateBookingStatusRequest request) {
        log.info("Попытка обновить статус брони с id {} на {}", id, request.getStatus());

        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Бронь с id " + id + " не найдена"));

        User currentUser = securityService.getCurrentUser();
        boolean isAdmin = securityService.isAdmin();

        bookingValidationService.validateBookingStatusTransition(booking, request.getStatus(), currentUser.getId(), isAdmin);
        booking.setStatus(request.getStatus());
        bookingRepository.save(booking);

        log.info("Статус брони с id {} успешно обновлен на {}", id, request.getStatus());
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional
    public MessageResponse delete(Long id) {
        log.info("Попытка удалить бронь с id {}", id);
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Бронь с id " + id + " не найдена"));
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
        boolean isAdmin = securityService.isAdmin();

        bookingValidationService.validateBookingOwnership(booking, currentUser.getId(), isAdmin);

        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional(readOnly = true)
    public BookingListResponse getBookings(
            Long restaurantId,
            Long userId,
            Long tableId,
            String status,
            LocalDateTime startTimeFrom,
            LocalDateTime startTimeTo,
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
        boolean isAdmin = securityService.isAdmin();

        Long effectiveUserId = userId;
        if (!isAdmin) {
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
                .and(BookingSpecification.byStartTimeFrom(startTimeFrom))
                .and(BookingSpecification.byStartTimeTo(startTimeTo));

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

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByRestaurant(Long restaurantId) {
        log.info("Получение бронирований для ресторана {}", restaurantId);
        bookingValidationService.validateRestaurantExists(restaurantId);
        List<Booking> bookings = bookingRepository.findAllByRestaurantId(restaurantId);
        return bookings.stream()
                .map(bookingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getBookingsByUser(Long userId) {
        log.info("Получение бронирований для пользователя {}", userId);
        User currentUser = securityService.getCurrentUser();
        boolean isAdmin = securityService.isAdmin();
        if (!isAdmin && !currentUser.getId().equals(userId)) {
            throw new AccessDeniedException("Вы можете просматривать только свои брони");
        }
        List<Booking> bookings = bookingRepository.findAllByUserId(userId);
        return bookings.stream()
                .map(bookingMapper::toResponse)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<BookingResponse> getMyBookings() {
        User currentUser = securityService.getCurrentUser();
        return getBookingsByUser(currentUser.getId());
    }

    private boolean hasTimeOrTableChanges(UpdateBookingRequest request) {
        return request.getTableId() != null || request.getStartTime() != null || request.getEndTime() != null;
    }
}
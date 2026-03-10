package goltsman.bookingtableapp.service.impl;


import goltsman.bookingtableapp.exception.ResourceAlreadyExistsException;
import goltsman.bookingtableapp.mapper.ReviewMapper;
import goltsman.bookingtableapp.model.entity.Restaurant;
import goltsman.bookingtableapp.model.entity.Review;
import goltsman.bookingtableapp.model.entity.User;
import goltsman.bookingtableapp.model.request.review.CreateReviewRequest;
import goltsman.bookingtableapp.model.request.review.UpdateReviewRequest;

import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantResponse;
import goltsman.bookingtableapp.model.responce.review.ReviewListResponse;
import goltsman.bookingtableapp.model.responce.review.ReviewResponse;
import goltsman.bookingtableapp.repository.RestaurantRepository;
import goltsman.bookingtableapp.repository.ReviewRepository;
import goltsman.bookingtableapp.repository.specification.ReviewSpecification;
import goltsman.bookingtableapp.security.SecurityService;
import goltsman.bookingtableapp.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final RestaurantRepository restaurantRepository;
    private final SecurityService securityService;

    @Override
    @Transactional
    public ReviewResponse create(CreateReviewRequest request) {
        log.info("Попытка создать отзыв для ресторана с id {}", request.getRestaurantId());
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new EntityNotFoundException("Ресторан с id " + request.getRestaurantId() + " не найден"));
        User currentUser = securityService.getCurrentUser();
        Review review = reviewMapper.toEntity(request);
        review.setRestaurant(restaurant);
        review.setUser(currentUser);
        reviewRepository.save(review);
        log.info("Отзыв успешно создан с id {}", review.getId());
        return reviewMapper.toResponse(review);
    }

    @Override
    @Transactional
    public ReviewResponse update(Long id, UpdateReviewRequest request) {
        log.info("Попытка обновить отзыв с id {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Отзыв с id " + id + " не найден"));
        User currentUser = securityService.getCurrentUser();
        if (!currentUser.getId().equals(review.getUser().getId()) && !securityService.isAdmin()) {
            throw new AccessDeniedException("У вас нет прав на обновление этого отзыва");
        }
        reviewMapper.updateEntity(review, request);
        reviewRepository.save(review);
        log.info("Отзыв с id {} успешно обновлен", id);
        return reviewMapper.toResponse(review);
    }

    @Override
    @Transactional
    public MessageResponse delete(Long id) {
        log.info("Попытка удалить отзыв с id {}", id);
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Отзыв с id " + id + " не найден"));
        User currentUser = securityService.getCurrentUser();
        if (!currentUser.getId().equals(review.getUser().getId()) && !securityService.isAdmin()) {
            throw new AccessDeniedException("У вас нет прав на удаление этого отзыва");
        }
        reviewRepository.delete(review);
        log.info("Отзыв с id {} успешно удален", id);
        return MessageResponse.builder().message("Отзыв успешно удален").build();
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponse getReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Отзыв с id " + id + " не найден"));
        return reviewMapper.toResponse(review);
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewListResponse getReviews(Long restaurantId, Long userId, Integer rating,
                                         Integer minRating, Integer maxRating, Pageable pageable) {
        Specification<Review> specification = Specification
                .where(ReviewSpecification.byRestaurantId(restaurantId))
                .and(ReviewSpecification.byUserId(userId))
                .and(ReviewSpecification.byRating(rating))
                .and(ReviewSpecification.byMinRating(minRating))
                .and(ReviewSpecification.byMaxRating(maxRating));
        Page<Review> page = reviewRepository.findAll(specification, pageable);
        var reviews = page.getContent().stream()
                .map(reviewMapper::toResponse)
                .toList();
        return ReviewListResponse.builder()
                .totalCount((int) page.getTotalElements())
                .page(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .limit(pageable.getPageSize())
                .reviews(reviews)
                .build();
    }
}

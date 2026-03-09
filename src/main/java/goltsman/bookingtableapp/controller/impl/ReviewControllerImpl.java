package goltsman.bookingtableapp.controller.impl;

import goltsman.bookingtableapp.controller.ReviewController;
import goltsman.bookingtableapp.model.request.review.CreateReviewRequest;
import goltsman.bookingtableapp.model.request.review.UpdateReviewRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.review.ReviewListResponse;
import goltsman.bookingtableapp.model.responce.review.ReviewResponse;
import goltsman.bookingtableapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewControllerImpl implements ReviewController {

    private final ReviewService reviewService;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReviewResponse> create(CreateReviewRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(reviewService.create(request));
    }

    @Override
    public ResponseEntity<ReviewResponse> getReview(Long id) {
        return ResponseEntity.ok(reviewService.getReview(id));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReviewResponse> update(Long id, UpdateReviewRequest request) {
        return ResponseEntity.ok(reviewService.update(id, request));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MessageResponse> delete(Long id) {
        return ResponseEntity.ok(reviewService.delete(id));
    }

    @Override
    public ResponseEntity<ReviewListResponse> getReviews(
            Long restaurantId,
            Long userId,
            Integer rating,
            Integer minRating,
            Integer maxRating,
            Integer page,
            Integer pageSize
    ) {

        PageRequest pageable = PageRequest.of(Math.max(page - 1, 0), Math.max(pageSize, 1));

        return ResponseEntity.ok(
                reviewService.getReviews(
                        restaurantId,
                        userId,
                        rating,
                        minRating,
                        maxRating,
                        pageable)
        );
    }
}
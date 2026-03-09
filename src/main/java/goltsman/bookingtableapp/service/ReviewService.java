package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.request.review.CreateReviewRequest;
import goltsman.bookingtableapp.model.request.review.UpdateReviewRequest;

import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.review.ReviewListResponse;
import goltsman.bookingtableapp.model.responce.review.ReviewResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewResponse create(CreateReviewRequest request);

    ReviewResponse update(Long id, UpdateReviewRequest request);

    MessageResponse delete(Long id);

    ReviewResponse getReview(Long id);

    ReviewListResponse getReviews(
            Long restaurantId,
            Long userId,
            Integer rating,
            Integer minRating,
            Integer maxRating,
            Pageable pageable
    );
}
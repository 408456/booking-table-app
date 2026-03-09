package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.entity.Review;
import goltsman.bookingtableapp.model.request.review.CreateReviewRequest;
import goltsman.bookingtableapp.model.request.review.UpdateReviewRequest;
import goltsman.bookingtableapp.model.responce.review.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Review toEntity(CreateReviewRequest request);

    void updateEntity(@MappingTarget Review review, UpdateReviewRequest request);

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.title", target = "restaurantTitle")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    ReviewResponse toResponse(Review review);
}

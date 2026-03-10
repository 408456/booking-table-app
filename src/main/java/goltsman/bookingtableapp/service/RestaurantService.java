package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.request.restaurant.CreateRestaurantRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateRestaurantRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantListResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantResponse;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantService {

    RestaurantResponse create(CreateRestaurantRequest request);

    RestaurantResponse update(Long id, UpdateRestaurantRequest request);

    MessageResponse delete(Long id);

    RestaurantResponse getRestaurant(Long id);

    RestaurantListResponse getRestaurants(
            String title,
            Long cuisineId,
            String address,
            BigDecimal minAvgSum,
            BigDecimal maxAvgSum,
            Boolean isPublished,
            Pageable pageable
    );

    List<RestaurantResponse> getRestaurantsByCuisine(Long cuisineId);

}
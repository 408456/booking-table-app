package goltsman.bookingtableapp.controller.impl;

import goltsman.bookingtableapp.controller.RestaurantController;
import goltsman.bookingtableapp.model.request.restaurant.CreateRestaurantRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateRestaurantRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantListResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantResponse;
import goltsman.bookingtableapp.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
@RequiredArgsConstructor
public class RestaurantControllerImpl implements RestaurantController {

    private final RestaurantService restaurantService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantResponse> create(CreateRestaurantRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(restaurantService.create(request));
    }

    @Override
    public ResponseEntity<RestaurantResponse> getRestaurant(Long id) {
        return ResponseEntity.ok(restaurantService.getRestaurant(id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RestaurantResponse> update(Long id, UpdateRestaurantRequest request) {
        return ResponseEntity.ok(restaurantService.update(id, request));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> delete(Long id) {
        return ResponseEntity.ok(restaurantService.delete(id));
    }

    @Override
    public ResponseEntity<RestaurantListResponse> getRestaurants(
            String title,
            Long cuisineId,
            String address,
            BigDecimal minAvgSum,
            BigDecimal maxAvgSum,
            Boolean isPublished,
            Integer page,
            Integer pageSize
    ) {

        PageRequest pageable = PageRequest.of(Math.max(page - 1, 0), Math.max(pageSize, 1));

        return ResponseEntity.ok(
                restaurantService.getRestaurants(
                        title,
                        cuisineId,
                        address,
                        minAvgSum,
                        maxAvgSum,
                        isPublished,
                        pageable
                )
        );
    }
}
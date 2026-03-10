package goltsman.bookingtableapp.service.impl;

import goltsman.bookingtableapp.exception.ResourceAlreadyExistsException;
import goltsman.bookingtableapp.mapper.RestaurantMapper;
import goltsman.bookingtableapp.model.entity.Cuisine;
import goltsman.bookingtableapp.model.entity.Restaurant;
import goltsman.bookingtableapp.model.request.restaurant.CreateRestaurantRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateRestaurantRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantListResponse;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantResponse;
import goltsman.bookingtableapp.repository.CuisineRepository;
import goltsman.bookingtableapp.repository.RestaurantRepository;
import goltsman.bookingtableapp.repository.specification.RestaurantSpecification;
import goltsman.bookingtableapp.service.RestaurantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    private final CuisineRepository cuisineRepository;

    @Override
    @Transactional
    public RestaurantResponse create(CreateRestaurantRequest request) {
        log.info("Попытка создать ресторан с названием {}", request.getTitle());
        if (restaurantRepository.existsByTitle(request.getTitle())) {
            throw new ResourceAlreadyExistsException(
                    "Ресторан с названием  " + request.getTitle() + " уже существует");}
        Restaurant restaurant = restaurantMapper.mapCreateRestaurantRequestToRestaurant(request);
        if (request.getCuisineIds() != null) {
            Set<Cuisine> cuisines =
                    new HashSet<>(cuisineRepository.findAllById(request.getCuisineIds()));

            restaurant.setCuisines(cuisines);
        }
        restaurantRepository.save(restaurant);
        log.info("Ресторан успешно создан с id {}", restaurant.getId());
        return restaurantMapper.mapRestaurantToRestaurantResponse(restaurant);
    }

    @Override
    @Transactional
    public RestaurantResponse update(Long id, UpdateRestaurantRequest request) {
        log.info("Попытка обновить ресторан с id {}", id);
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ресторан с id " + id + " не найден"));
        if (request.getTitle() != null &&
                restaurantRepository.existsByTitleAndIdNot(request.getTitle(), id)) {
            throw new ResourceAlreadyExistsException(
                    "Ресторан с названием " + request.getTitle() + " уже существует");
        }
        restaurantMapper.mapUpdateRestaurantRequestToRestaurant(request, restaurant);
        if (request.getCuisineIds() != null) {
            Set<Cuisine> cuisines =
                    new HashSet<>(cuisineRepository.findAllById(request.getCuisineIds()));
            restaurant.setCuisines(cuisines);
        }

        restaurantRepository.save(restaurant);

        log.info("Ресторан с id {} успешно обновлен", id);

        return restaurantMapper.mapRestaurantToRestaurantResponse(restaurant);
    }

    @Override
    @Transactional
    public MessageResponse delete(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ресторан с id " + id + " не найден"));
        restaurantRepository.delete(restaurant);
        return MessageResponse.builder().message("Ресторан успешно удален").build();
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantResponse getRestaurant(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ресторан с id " + id + " не найден"));
        return restaurantMapper.mapRestaurantToRestaurantResponse(restaurant);
    }

    @Override
    @Transactional(readOnly = true)
    public RestaurantListResponse getRestaurants(
            String title,
            Long cuisineId,
            String address,
            BigDecimal minAvgSum,
            BigDecimal maxAvgSum,
            Boolean isPublished,
            Pageable pageable
    ) {

        Specification<Restaurant> specification = Specification
                .where(RestaurantSpecification.titleContains(title))
                .and(RestaurantSpecification.cuisineId(cuisineId))
                .and(RestaurantSpecification.addressContains(address))
                .and(RestaurantSpecification.minAvgSum(minAvgSum))
                .and(RestaurantSpecification.maxAvgSum(maxAvgSum))
                .and(RestaurantSpecification.isPublished(isPublished));

        Page<Restaurant> page = restaurantRepository.findAll(specification, pageable);
        List<RestaurantResponse> restaurants = page.getContent()
                .stream()
                .map(restaurantMapper::mapRestaurantToRestaurantResponse)
                .toList();
        return RestaurantListResponse.builder()
                .totalCount((int) page.getTotalElements())
                .page(pageable.getPageNumber() + 1)
                .pageSize(pageable.getPageSize())
                .limit(pageable.getPageSize())
                .restaurants(restaurants)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestaurantResponse> getRestaurantsByCuisine(Long cuisineId) {
        log.info("Получение ресторанов по id кухни {}", cuisineId);
        List<Restaurant> restaurants = restaurantRepository.findAllByCuisineId(cuisineId);
        return restaurants.stream()
                .map(restaurantMapper::mapRestaurantToRestaurantResponse)
                .toList();
    }
}
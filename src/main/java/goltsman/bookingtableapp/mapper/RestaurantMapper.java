package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.entity.Restaurant;
import goltsman.bookingtableapp.model.request.restaurant.CreateRestaurantRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateRestaurantRequest;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "cuisines", ignore = true)
    Restaurant mapCreateRestaurantRequestToRestaurant(CreateRestaurantRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "cuisines", ignore = true)
    void mapUpdateRestaurantRequestToRestaurant(
            UpdateRestaurantRequest request,
            @MappingTarget Restaurant restaurant);

    RestaurantResponse mapRestaurantToRestaurantResponse(Restaurant restaurant);
}

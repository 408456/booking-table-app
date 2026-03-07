package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.entity.Restaurant;
import goltsman.bookingtableapp.model.request.restaurant.CreateRestaurantRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateRestaurantRequest;
import goltsman.bookingtableapp.model.responce.restaurant.RestaurantResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    Restaurant mapCreateRestaurantRequestToRestaurant(CreateRestaurantRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapUpdateRestaurantRequestToRestaurant(UpdateRestaurantRequest request,
                                                @MappingTarget Restaurant restaurant);

    RestaurantResponse mapRestaurantToRestaurantResponse(Restaurant restaurant);

}

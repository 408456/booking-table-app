package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.entity.Cuisine;
import  goltsman.bookingtableapp.model.request.cuisine.CreateCuisineRequest;
import goltsman.bookingtableapp.model.responce.restaurant.CuisineResponse;
import goltsman.bookingtableapp.model.request.cuisine.UpdateCuisineRequest;
import org.mapstruct.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CuisineMapper {

    Cuisine mapCreateCuisineRequestToCuisine(CreateCuisineRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapUpdateCuisineRequestToCuisine(UpdateCuisineRequest request,
                                          @MappingTarget Cuisine cuisine);

    CuisineResponse mapCuisineToCuisineResponse(Cuisine cuisine);
}

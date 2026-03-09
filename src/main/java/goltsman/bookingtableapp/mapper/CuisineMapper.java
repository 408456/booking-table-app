package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.entity.Cuisine;
import goltsman.bookingtableapp.model.request.restaurant.CreateCuisineRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateCuisineRequest;
import goltsman.bookingtableapp.model.responce.restaurant.CuisineResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CuisineMapper {

    Cuisine mapCreateCuisineRequestToCuisine(CreateCuisineRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapUpdateCuisineRequestToCuisine(UpdateCuisineRequest request,
                                          @MappingTarget Cuisine cuisine);

    CuisineResponse mapCuisineToCuisineResponse(Cuisine cuisine);
}

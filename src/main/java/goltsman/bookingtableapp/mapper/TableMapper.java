package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.entity.TableEntity;
import goltsman.bookingtableapp.model.request.restaurant.CreateTableRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateTableRequest;
import goltsman.bookingtableapp.model.responce.restaurant.TableResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TableMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    TableEntity toEntity(CreateTableRequest request);

    void updateEntity(@MappingTarget TableEntity table, UpdateTableRequest request);

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.title", target = "restaurantTitle")
    TableResponse toResponse(TableEntity table);
}
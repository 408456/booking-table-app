package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.entity.Booking;
import goltsman.bookingtableapp.model.request.booking.CreateBookingRequest;
import goltsman.bookingtableapp.model.request.booking.UpdateBookingRequest;
import goltsman.bookingtableapp.model.responce.booking.BookingResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "table", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Booking toEntity(CreateBookingRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Booking booking, UpdateBookingRequest request);

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.title", target = "restaurantTitle")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.firstName", target = "userFirstName")
    @Mapping(source = "user.lastName", target = "userLastName")
    @Mapping(source = "table.id", target = "tableId")
    @Mapping(source = "table.seats", target = "tableSeats")
    BookingResponse toResponse(Booking booking);
}
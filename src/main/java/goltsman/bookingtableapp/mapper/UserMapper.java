package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.User;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.request.UpdateUserProfileRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User mapCreateUserRequestToUser(CreateUserRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapUpdateUserProfileRequestToUser(UpdateUserProfileRequest request,
                                           @MappingTarget User user);

    UserResponse mapUserToUserResponse(User user);
}

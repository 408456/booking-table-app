package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.entity.User;
import goltsman.bookingtableapp.model.request.user.CreateUserRequest;
import goltsman.bookingtableapp.model.request.auth.SignUpRequest;
import goltsman.bookingtableapp.model.request.user.UpdateUserProfileRequest;
import goltsman.bookingtableapp.model.responce.user.UserResponse;
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", expression = "java(RoleType.CLIENT)")
    @Mapping(target = "isVerified", constant = "false")
    User mapSingUpRequesttoUser(SignUpRequest request);

    UserResponse mapUserToUserResponse(User user);
}

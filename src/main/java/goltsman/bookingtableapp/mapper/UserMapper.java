package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.User;
import goltsman.bookingtableapp.model.enums.RoleType;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "isVerified", constant = "false")
    User toUser(CreateUserRequest request);

    @Mapping(target = "role", source = "user", qualifiedByName = "mapRole")
    UserResponse toCreateUserResponse(User user);

    @Mapping(target = "role", source = "user", qualifiedByName = "mapRole")
    UserResponse toUpdateUserResponse(User user);

    @Named("mapRole")
    default RoleType mapRole(User user) {
        return user.getUserRoles().stream()
                .findFirst()
                .map(userRole -> userRole.getRole().getName())
                .orElse(null);
    }
}

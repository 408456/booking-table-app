package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.User;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "isVerified", constant = "false")
    User toUser(CreateUserRequest request);

    UserResponse toCreateUserResponse(User user);

    UserResponse toUpdateUserResponse(User user);
}

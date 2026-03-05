package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.request.UpdateUserProfileRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.UserListResponse;
import goltsman.bookingtableapp.model.responce.UserResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse create(CreateUserRequest createUserRequest);

    UserResponse updateProfile(UpdateUserProfileRequest updateUserProfileRequest);

    UserResponse getUser(Long id);

    MessageResponse delete(Long id);

    UserListResponse getUsers(Pageable pageable);

}

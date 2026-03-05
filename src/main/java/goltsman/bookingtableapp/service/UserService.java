package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.request.UpdateUserProfileRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;

public interface UserService {
    UserResponse create(CreateUserRequest createUserRequest);

    UserResponse updateProfile(UpdateUserProfileRequest updateUserProfileRequest);
//
//    UserResponse getById(Long id);
//
//    UserListResponse getUsers(Pageable pageable);
//
//    UserResponse delete(Long id);
}

package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;

public interface UserService {
    UserResponse createUser(CreateUserRequest createUserRequest);
}

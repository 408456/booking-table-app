package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.dto.JwtAuthenticationDto;
import goltsman.bookingtableapp.model.dto.RefreshTokenDto;
import goltsman.bookingtableapp.model.dto.UserCredentialsDto;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;

import javax.naming.AuthenticationException;

public interface UserService {
    UserResponse createUser(CreateUserRequest createUserRequest);
    JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException;
    JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception;

}

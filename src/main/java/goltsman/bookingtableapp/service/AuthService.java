package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.request.RefreshTokenRequest;
import goltsman.bookingtableapp.model.request.UserCredentialsRequest;
import goltsman.bookingtableapp.model.responce.JwtAuthenticationResponse;

public interface AuthService {
    JwtAuthenticationResponse signIn(UserCredentialsRequest request);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);
}

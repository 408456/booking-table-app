package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.request.RefreshTokenRequest;
import goltsman.bookingtableapp.model.request.SignUpRequest;
import goltsman.bookingtableapp.model.request.SignInRequest;
import goltsman.bookingtableapp.model.responce.JwtResponse;
import goltsman.bookingtableapp.model.responce.MessageResponse;

public interface AuthService {
    JwtResponse signIn(SignInRequest request);

    JwtResponse refreshToken(RefreshTokenRequest request);

    MessageResponse signUp(SignUpRequest request);
}

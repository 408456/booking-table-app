package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.request.auth.RefreshTokenRequest;
import goltsman.bookingtableapp.model.request.auth.SignUpRequest;
import goltsman.bookingtableapp.model.request.auth.SignInRequest;
import goltsman.bookingtableapp.model.responce.auth.JwtResponse;
import goltsman.bookingtableapp.model.responce.MessageResponse;

public interface AuthService {
    JwtResponse signIn(SignInRequest request);

    JwtResponse refreshToken(RefreshTokenRequest request);

    MessageResponse signUp(SignUpRequest request);
}

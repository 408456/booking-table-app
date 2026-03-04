package goltsman.bookingtableapp.controller.impl;

import goltsman.bookingtableapp.controller.AuthController;
import goltsman.bookingtableapp.model.dto.JwtAuthenticationDto;
import goltsman.bookingtableapp.model.request.RefreshTokenRequest;
import goltsman.bookingtableapp.model.request.UserCredentialsRequest;
import goltsman.bookingtableapp.model.responce.JwtAuthenticationResponse;
import goltsman.bookingtableapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    public ResponseEntity<JwtAuthenticationResponse> singIn(UserCredentialsRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> refresh(RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request));
    }
}

package goltsman.bookingtableapp.controller.impl;

import goltsman.bookingtableapp.controller.AuthController;
import goltsman.bookingtableapp.model.request.RefreshTokenRequest;
import goltsman.bookingtableapp.model.request.SignUpRequest;
import goltsman.bookingtableapp.model.request.SignInRequest;
import goltsman.bookingtableapp.model.responce.JwtResponse;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    public ResponseEntity<JwtResponse> singIn(SignInRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signIn(request));
    }

    @Override
    public ResponseEntity<MessageResponse> signUp(SignUpRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(request));
    }

    @Override
    public ResponseEntity<JwtResponse> refresh(RefreshTokenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.refreshToken(request));
    }
}

package goltsman.bookingtableapp.controller.impl;

import goltsman.bookingtableapp.controller.AuthController;
import goltsman.bookingtableapp.model.dto.JwtAuthenticationDto;
import goltsman.bookingtableapp.model.dto.RefreshTokenDto;
import goltsman.bookingtableapp.model.dto.UserCredentialsDto;
import goltsman.bookingtableapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {
    private final UserService userService;

    @Override
    public ResponseEntity<JwtAuthenticationDto> singIn(UserCredentialsDto userCredentialsDto) {
        try {
            JwtAuthenticationDto jwtAuthenticationDto = userService.singIn(userCredentialsDto);
            return ResponseEntity.ok(jwtAuthenticationDto);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

    }

    @Override
    public JwtAuthenticationDto refresh(RefreshTokenDto refreshTokenDto) {
        try {
            return userService.refreshToken(refreshTokenDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

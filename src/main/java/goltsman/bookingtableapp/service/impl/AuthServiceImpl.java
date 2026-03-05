package goltsman.bookingtableapp.service.impl;

import goltsman.bookingtableapp.mapper.AuthMapper;
import goltsman.bookingtableapp.model.User;
import goltsman.bookingtableapp.model.request.RefreshTokenRequest;
import goltsman.bookingtableapp.model.request.UserCredentialsRequest;
import goltsman.bookingtableapp.model.responce.JwtAuthenticationResponse;
import goltsman.bookingtableapp.repository.UserRepository;
import goltsman.bookingtableapp.security.jwt.JwtService;
import goltsman.bookingtableapp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthMapper authMapper;

    @Override
    public JwtAuthenticationResponse signIn(UserCredentialsRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Неверная почта"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Неверный пароль");
        }

        JwtAuthenticationResponse response = JwtAuthenticationResponse.builder()
                .token(jwtService.generateAccessToken(user.getId()))
                .refreshToken(jwtService.generateRefreshToken(user.getId()))
                .build();

        log.info("Пользователь {} вошел в систему", user.getEmail());
        return response;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) {

        var claims = jwtService.parseToken(request.getRefreshToken());

        if (!jwtService.isRefreshToken(claims)) {
            throw new BadCredentialsException("Неверный тип токена");
        }

        Long userId = jwtService.extractUserId(claims);

        userRepository.findById(userId)
                .orElseThrow(() -> new BadCredentialsException("Пользователь не найден"));

        return JwtAuthenticationResponse.builder()
                .token(jwtService.generateAccessToken(userId))
                .refreshToken(request.getRefreshToken())
                .build();
    }
}

package goltsman.bookingtableapp.service.impl;

import goltsman.bookingtableapp.mapper.UserMapper;
import goltsman.bookingtableapp.model.User;
import goltsman.bookingtableapp.model.request.RefreshTokenRequest;
import goltsman.bookingtableapp.model.request.SignUpRequest;
import goltsman.bookingtableapp.model.request.SignInRequest;
import goltsman.bookingtableapp.model.responce.JwtResponse;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.repository.UserRepository;
import goltsman.bookingtableapp.security.jwt.JwtService;
import goltsman.bookingtableapp.service.AuthService;
import goltsman.bookingtableapp.service.UserValidationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final UserValidationService userValidationService;

    @Override
    @Transactional
    public JwtResponse signIn(SignInRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Неверная почта"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new BadCredentialsException("Неверный пароль");

        log.info("Пользователь {} вошел в систему", user.getEmail());
        return JwtResponse.builder()
                .token(jwtService.generateAccessToken(user.getId()))
                .refreshToken(jwtService.generateRefreshToken(user.getId()))
                .build();
    }

    @Override
    @Transactional
    public JwtResponse refreshToken(RefreshTokenRequest request) {

        var claims = jwtService.parseToken(request.getRefreshToken());
        if (!jwtService.isRefreshToken(claims)) throw new BadCredentialsException("Неверный тип токена");
        Long userId = jwtService.extractUserId(claims);

        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
        return JwtResponse.builder()
                .token(jwtService.generateAccessToken(userId))
                .refreshToken(request.getRefreshToken())
                .build();
    }

    @Override
    @Transactional
    public MessageResponse signUp(SignUpRequest request) {
        userValidationService.validateEmailForCreate(request.getEmail());
        userValidationService.validatePhoneForCreate(request.getPhone());
        User user = userMapper.mapSingUpRequesttoUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        log.info("Зарегистрирован новый пользователь {}", savedUser.getEmail());
        // TODO: реализация верификации
        return MessageResponse.builder().message("Пользователь успешно зарегестрирован").build();
    }
}

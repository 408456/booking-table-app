package goltsman.bookingtableapp.service.impl;

import goltsman.bookingtableapp.exception.ResourceAlreadyExistsException;
import goltsman.bookingtableapp.mapper.UserMapper;
import goltsman.bookingtableapp.model.Role;
import goltsman.bookingtableapp.model.User;
import goltsman.bookingtableapp.model.UserRole;
import goltsman.bookingtableapp.model.dto.JwtAuthenticationDto;
import goltsman.bookingtableapp.model.dto.RefreshTokenDto;
import goltsman.bookingtableapp.model.dto.UserCredentialsDto;
import goltsman.bookingtableapp.model.enums.RoleType;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;
import goltsman.bookingtableapp.repository.RoleRepository;
import goltsman.bookingtableapp.repository.UserRepository;
import goltsman.bookingtableapp.security.jwt.JwtService;
import goltsman.bookingtableapp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByPhone(request.getPhone()) ||
                userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException("Пользователь с такими данными уже существует");
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        RoleType roleType = RoleType.valueOf(request.getRole());
        log.info("roleType={}", roleType);
        Role role = roleRepository.findByName(roleType)
                .orElseThrow(() -> new IllegalArgumentException("Роль не найдена: " + roleType));
        UserRole userRole = UserRole.builder()
                .user(user)
                .role(role)
                .build();
        user.getUserRoles().add(userRole);

        userRepository.save(user);
        return userMapper.toCreateUserResponse(user);
    }

    @Override
    public JwtAuthenticationDto singIn(UserCredentialsDto userCredentialsDto) throws AuthenticationException {
        User user = findByCredentials(userCredentialsDto); // используем приватный метод
        return jwtService.generateAuthToken(user.getEmail());
    }

    @Override
    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) throws Exception {
        String refreshToken = refreshTokenDto.getRefreshToken();
        if (refreshToken != null && jwtService.validateJwtToken(refreshToken)) {
            User user = findByEmail(jwtService.getEmailFromToken(refreshToken));
            return jwtService.refreshBaseToken(user.getEmail(), refreshToken);
        }
        throw new AuthenticationException("Invalid refresh token");
    }

    private User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
    }

    private User findByCredentials(UserCredentialsDto dto) throws AuthenticationException {
        User user = findByEmail(dto.getEmail());
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid credentials");
        }
        return user;
    }
}

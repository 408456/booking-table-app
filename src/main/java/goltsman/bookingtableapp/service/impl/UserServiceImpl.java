package goltsman.bookingtableapp.service.impl;

import goltsman.bookingtableapp.mapper.UserMapper;
import goltsman.bookingtableapp.model.User;
import goltsman.bookingtableapp.model.dto.JwtAuthenticationDto;
import goltsman.bookingtableapp.model.dto.RefreshTokenDto;
import goltsman.bookingtableapp.model.dto.UserCredentialsDto;
import goltsman.bookingtableapp.model.enums.RoleType;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.request.UpdateUserProfileRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;
import goltsman.bookingtableapp.repository.UserRepository;
import goltsman.bookingtableapp.security.SecurityService;
import goltsman.bookingtableapp.security.jwt.JwtService;
import goltsman.bookingtableapp.service.UserService;
import goltsman.bookingtableapp.service.UserValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final SecurityService securityService;
    private final UserValidationService userValidationService;

    @Override
    @Transactional
    public UserResponse create(CreateUserRequest request) {
        log.info("Попытка создать пользователя с email {}", request.getEmail());

        userValidationService.validateEmailForCreate(request.getEmail());
        userValidationService.validatePhoneForCreate(request.getPhone());
        User user = userMapper.mapCreateUserRequestToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(RoleType.valueOf(request.getRole()));
        userRepository.save(user);
        log.info("Пользователь успешно создан с id {}", user.getId());

        return userMapper.mapUserToUserResponse(user);
    }


    @Override
    @Transactional
    public UserResponse updateProfile(UpdateUserProfileRequest request) {
        User user = securityService.getCurrentUser();
        log.info("Попытка обновить профиль пользователя с id {}", user.getId());

        userValidationService.validateEmailForUpdate(request.getEmail(), user);
        userValidationService.validatePhoneForUpdate(request.getPhone(), user);

        userMapper.mapUpdateUserProfileRequestToUser(request, user);
        if (userValidationService.isEmailChanged(request.getEmail(), user)) user.setIsVerified(false);

        userRepository.save(user);
        log.info("Профиль пользователя с id {} успешно обновлен", user.getId());
        return userMapper.mapUserToUserResponse(user);
    }


//
//    @Override
//    @Transactional(readOnly = true)
//    public UserResponse getById(Long id) {
//        log.info("Попытка получить данные пользователя с id {}", id);
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Пользователь с id=" + id + " не найден"));
//        return userMapper.mapUsertoCreateUserResponse(user);
//    }
//
//
//    @Transactional(readOnly = true)
//    public UserListResponse getUsers(Pageable pageable) {
//        log.info("Попытка получить список пользователей");
//        Page<User> userPage = userRepository.findAll(pageable);
//        List<UserResponse> users = userPage.getContent().stream()
//                .map(userMapper::mapUsertoCreateUserResponse)
//                .toList();
//        return new UserListResponse(
//                (int) userPage.getTotalElements(),
//                pageable.getPageNumber() + 1,
//                pageable.getPageSize(),
//                pageable.getPageSize(),
//                users
//        );
//    }
//
//
//    @Override
//    public UserResponse delete(Long id) {
//        log.info("Попытка удалить пользователя с id {}", id);
//
//        return null;
//    }
}

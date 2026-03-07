package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.mapper.UserMapper;
import goltsman.bookingtableapp.model.User;
import goltsman.bookingtableapp.model.enums.RoleType;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;
import goltsman.bookingtableapp.repository.UserRepository;
import goltsman.bookingtableapp.security.SecurityService;
import goltsman.bookingtableapp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Модульное тестирование UserServiceImpl")
public class UserServiceUnitTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidationService userValidationService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("создание пользователя")
    void createUser_success1() {
        CreateUserRequest request = buildUserRequest();
        User user = new User();
        UserResponse expectedResponse =buildUserResponse();

        doNothing().when(userValidationService).validateEmailForCreate(request.getEmail());
        doNothing().when(userValidationService).validatePhoneForCreate(request.getPhone());
        when(userMapper.mapCreateUserRequestToUser(request)).thenReturn(user);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userMapper.mapUserToUserResponse(user)).thenReturn(expectedResponse);

        UserResponse actualResponse = userService.create(request);

        assertNotNull(actualResponse);
        assertEquals(expectedResponse, actualResponse);
        assertEquals("encodedPassword", user.getPassword());
        assertEquals(RoleType.CLIENT, user.getRole());

        verify(userValidationService).validateEmailForCreate(request.getEmail());
        verify(userValidationService).validatePhoneForCreate(request.getPhone());
        verify(userRepository).save(user);
        verify(userMapper).mapUserToUserResponse(user);
    }

    CreateUserRequest buildUserRequest() {
        return CreateUserRequest.builder()
                .firstName("Пользователь")
                .lastName("Пользователь")
                .email("valid_user_for_test@gmail.com")
                .phone("+79990001122")
                .password("My_password09!")
                .role("CLIENT")
                .build();
    }

    UserResponse buildUserResponse() {
        return UserResponse.builder()
                .firstName("Пользователь")
                .lastName("Пользователь")
                .phone("+79990001122")
                .email("valid_user_for_test@gmail.com")
                .role(RoleType.CLIENT)
                .isVerified(false)
                .build();
    }


}

package goltsman.bookingtableapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import goltsman.bookingtableapp.mapper.UserMapper;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static goltsman.bookingtableapp.common.ApiConstant.BASE_USER_CONTROLLER_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DBRider
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Интеграционные тесты для UserController")
class UserControllerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserMapper userMapper;

    @BeforeEach
    void cleanDb() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("create 201: успешное создание пользователя")
    void createUser_withValidData_shouldReturnCreatedUser() throws Exception {
        CreateUserRequest request = createValidUserForTest();

        mockMvc.perform(post(BASE_USER_CONTROLLER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value("valid_user_for_test@gmail.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("create 400: неправильные аргументы запроса при создании пользователя")
    void createUser_withInvalidData_shouldReturnBadRequest() throws Exception {
        CreateUserRequest request = createInvalidUserForTest();
        System.out.println(objectMapper.writeValueAsString(request));
        mockMvc.perform(post(BASE_USER_CONTROLLER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("create 401: некорректный токен авторизации")
    void createUserWithoutRoles_shouldReturnForbidden() throws Exception {
        CreateUserRequest request = createValidUserForTest();
        mockMvc.perform(post(BASE_USER_CONTROLLER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    @DisplayName("create 403: клиент создает пользователя")
    void clientCreateUser_shouldReturnForbidden() throws Exception {
        CreateUserRequest request = createValidUserForTest();
        mockMvc.perform(post(BASE_USER_CONTROLLER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("create 409: пользователь уже существует")
    void createUserTwice_shouldReturnConflict() throws Exception {
        CreateUserRequest request = createValidUserForTest();
        userRepository.save(userMapper.mapCreateUserRequestToUser(request));
        CreateUserRequest sameRequest = createValidUserForTest();
        mockMvc.perform(post(BASE_USER_CONTROLLER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(objectMapper.writeValueAsString(sameRequest)))
                .andExpect(status().isConflict());
    }

    CreateUserRequest createValidUserForTest() {
        return new CreateUserRequest(
                "Пользователь",
                "Пользователь",
                "+79000000000",
                "valid_user_for_test@gmail.com",
                "My_password09!",
                "ADMIN"
        );
    }

    CreateUserRequest createInvalidUserForTest() {
        return new CreateUserRequest(
                "Пользователь",
                "Пользователь",
                "INVALID PHONE",
                "invalid_user_for_test@gmail.com",
                "My_password09!",
                "ADMIN"
        );
    }
}
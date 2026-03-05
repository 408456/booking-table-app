package goltsman.bookingtableapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static goltsman.bookingtableapp.common.ApiConstant.BASE_USER_CONTROLLER_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.yml")
@ActiveProfiles("test")
@DisplayName("Интеграционные тесты для UserController")
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanDB() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("201: успешное создание пользователя")
    void createUser_withValidData_shouldReturnCreatedUser() throws Exception {

        CreateUserRequest request = createValidUserForTest();

        mockMvc.perform(post(BASE_USER_CONTROLLER_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.firstName").value("Пользователь"))
                .andExpect(jsonPath("$.lastName").value("Пользователь"))
                .andExpect(jsonPath("$.phone").value("+79000000000"))
                .andExpect(jsonPath("$.email").value("valid_user_for_test@gmail.com"));
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
                "+79000000010",
                "invalid_user_for_test@gmail.com",
                "My_password09!",
                "НЕСУЩЕСТВУЮЩАЯ РОЛЬ"
        );
    }
}
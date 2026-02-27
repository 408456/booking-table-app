package goltsman.bookingtableapp.service.impl;

import goltsman.bookingtableapp.exception.ResourceAlreadyExistsException;
import goltsman.bookingtableapp.mapper.UserMapper;
import goltsman.bookingtableapp.model.User;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;
import goltsman.bookingtableapp.repository.UserRepository;
import goltsman.bookingtableapp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        log.info("Попытка создать пользователя с именем '{}'", request.getFirstName());

        if (userRepository.existsByPhone(request.getPhone()) ||
                userRepository.existsByEmail(request.getPhone())) {
            throw new ResourceAlreadyExistsException("Пользователь с такими данными уже существует");
        }

        User user = userMapper.toUser(request);
        userRepository.save(user);
        log.info("Пользователь '{}' успешно создан с id={}", user.getUsername(), user.getId());
        return userMapper.toCreateUserResponse(user);
    }

    @Transactional
    public User createUser(User user) {
        log.info("Попытка создать пользователя с username='{}'", user.getUsername());
        User saved = userRepository.save(user);
        log.info("Пользователь '{}' успешно создан с id={}", saved.getUsername(), saved.getId());
        return saved;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}

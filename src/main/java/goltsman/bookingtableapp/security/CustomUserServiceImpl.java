package goltsman.bookingtableapp.security;

import goltsman.bookingtableapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl {

    private final UserRepository repository;

    public CustomUserDetails loadUserByEmail(String email) {
        return repository.findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с email" + email + " не найден"));
    }

    public CustomUserDetails loadUserById(Long id) {
        return repository.findById(id)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь c id " + id + " не найден"));
    }
}
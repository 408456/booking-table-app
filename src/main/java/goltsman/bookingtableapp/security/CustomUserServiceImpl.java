package goltsman.bookingtableapp.security;

import goltsman.bookingtableapp.model.User;
import goltsman.bookingtableapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User getCurrentUser() {
        log.info("Попытка получить текущего пользователя из SecurityContext");

        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("Пользователь не аутентифицирован");
        }
        if (!(principal instanceof CustomUserDetails customUserDetails)) {
            log.warn("Principal не является CustomUserDetails");
            throw new AuthenticationCredentialsNotFoundException("Пользователь не аутентифицирован");
        }

        return customUserDetails.user();
    }
}
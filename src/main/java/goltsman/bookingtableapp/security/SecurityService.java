package goltsman.bookingtableapp.security;

import goltsman.bookingtableapp.model.User;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecurityService {

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

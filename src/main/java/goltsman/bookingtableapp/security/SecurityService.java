package goltsman.bookingtableapp.security;

import goltsman.bookingtableapp.model.User;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
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

        if (!(principal instanceof CustomUserDetails customUserDetails)) {
            log.warn("Пользователь не аутентифицирован или principal не является CustomUserDetails");
            throw new AuthenticationCredentialsNotFoundException("Пользователь не аутентифицирован");
        }

        return customUserDetails.user();
    }
}

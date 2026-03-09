package goltsman.bookingtableapp.security.jwt;

import goltsman.bookingtableapp.security.CustomUserDetails;
import goltsman.bookingtableapp.security.CustomUserServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.debug("Входящий Authorization header: {}", header);

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            log.info("Получен JWT токен: {}", token);

            try {
                Claims claims = jwtService.parseToken(token);
                log.debug("JWT claims: {}", claims);

                if (!jwtService.isAccessToken(claims)) {
                    log.warn("Попытка использовать не access-токен");
                    throw new RuntimeException("Неверный тип токена");
                }

                Long userId = jwtService.extractUserId(claims);
                log.info("Из токена извлечен userId: {}", userId);

                CustomUserDetails userDetails = userService.loadUserById(userId);
                log.debug("Загружены UserDetails: {}", userDetails);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);

                log.info("Authentication успешно установлен для userId {}", userId);

            } catch (Exception e) {
                log.error("Ошибка при обработке JWT: {}", e.getMessage(), e);
            }
        } else {
            log.debug("JWT токен не найден или заголовок не Bearer");
        }

        filterChain.doFilter(request, response);
    }
}
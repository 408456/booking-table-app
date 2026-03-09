package goltsman.bookingtableapp.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey signingKey;

    private static final String TYPE = "type";
    private static final String ACCESS = "ACCESS";
    private static final String REFRESH = "REFRESH";

    @PostConstruct
    void init() {
        signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        log.info("JWT signing key инициализирована");
    }

    public String generateAccessToken(Long userId) {
        log.info("Генерация access-токена для userId {}", userId);
        return buildToken(userId, ACCESS, 60);
    }

    public String generateRefreshToken(Long userId) {
        log.info("Генерация refresh-токена для userId {}", userId);
        return buildToken(userId, REFRESH, 1440);
    }

    private String buildToken(Long userId, String type, long minutes) {
        Instant now = Instant.now();
        String token = Jwts.builder()
                .setSubject(userId.toString())
                .claim(TYPE, type)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(minutes, ChronoUnit.MINUTES)))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
        log.debug("Сформирован токен: {}", token);
        return token;
    }

    public Claims parseToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.info("Токен успешно распарсен, claims: {}", claims);
            return claims;
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Некорректный или просроченный токен: {}", e.getMessage());
            throw new BadCredentialsException("Некорректный или просроченный токен");
        }
    }

    public Long extractUserId(Claims claims) {
        Long userId = Long.parseLong(claims.getSubject());
        log.debug("Извлечен userId из claims: {}", userId);
        return userId;
    }

    public boolean isAccessToken(Claims claims) {
        boolean result = ACCESS.equals(claims.get(TYPE));
        log.debug("Проверка типа токена: {}", result);
        return result;
    }

    public boolean isRefreshToken(Claims claims) {
        boolean result = REFRESH.equals(claims.get(TYPE));
        log.debug("Проверка типа refresh-токена: {}", result);
        return result;
    }
}
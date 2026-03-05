package goltsman.bookingtableapp.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
    }

    public String generateAccessToken(Long userId) {
        return buildToken(userId, ACCESS, 60);
    }

    public String generateRefreshToken(Long userId) {
        return buildToken(userId, REFRESH, 1440);
    }

    private String buildToken(Long userId, String type, long minutes) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(userId.toString())
                .claim(TYPE, type)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(minutes, ChronoUnit.MINUTES)))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(signingKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Некорректный или просроченный токен");
        }
    }

    public Long extractUserId(Claims claims) {
        return Long.parseLong(claims.getSubject());
    }

    public boolean isAccessToken(Claims claims) {
        return ACCESS.equals(claims.get(TYPE));
    }

    public boolean isRefreshToken(Claims claims) {
        return REFRESH.equals(claims.get(TYPE));
    }
}
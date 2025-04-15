package com.boilerplate.javaspringbootboilerplate.application.auth;

import com.boilerplate.javaspringbootboilerplate.application.exception.BoilerplateException;
import com.boilerplate.javaspringbootboilerplate.application.exception.ErrorCode;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperty.class)
public class JwtUtil {

    private final JwtProperty jwtProperty;

    private SecretKey getAccessSecretKey() {
        String encoded = Base64.getEncoder().encodeToString(jwtProperty.getAccessTokenSecretKey().getBytes());
        return Keys.hmacShaKeyFor(encoded.getBytes());
    }

    private SecretKey getRefreshSecretKey() {
        String encoded = Base64.getEncoder().encodeToString(jwtProperty.getRefreshTokenSecretKey().getBytes());
        return Keys.hmacShaKeyFor(encoded.getBytes());
    }

    public String reissue(LocalDateTime now, String refreshToken) {
        String memberId = Jwts.parserBuilder()
            .setSigningKey(getRefreshSecretKey())
            .build()
            .parseClaimsJws(refreshToken)
            .getBody()
            .getSubject();

        return generateAccessToken(now, Long.valueOf(memberId));
    }

    public String generateAccessToken(LocalDateTime now, Long memberId) {
        Instant instant = now.toInstant(ZoneOffset.UTC);

        return Jwts.builder()
            .setSubject(memberId.toString())
            .setIssuedAt(Date.from(instant))
            .setExpiration(Date.from(instant.plusMillis(jwtProperty.getAccessTokenExpires())))
            .signWith(getAccessSecretKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    public String generateRefreshToken(LocalDateTime now, Long memberId) {
        Instant instant = now.toInstant(ZoneOffset.UTC);

        return Jwts.builder()
            .setSubject(memberId.toString())
            .setIssuedAt(Date.from(instant))
            .setExpiration(Date.from(instant.plusMillis(jwtProperty.getRefreshTokenExpires())))
            .signWith(getRefreshSecretKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    public String getSubject(String accessToken) {
        return Jwts.parserBuilder()
            .setSigningKey(getAccessSecretKey())
            .build()
            .parseClaimsJws(accessToken)
            .getBody()
            .getSubject();
    }

    public String getAccessToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new BoilerplateException(ErrorCode.UNAUTHORIZED);
        }
        return authorization.substring(7);
    }
}

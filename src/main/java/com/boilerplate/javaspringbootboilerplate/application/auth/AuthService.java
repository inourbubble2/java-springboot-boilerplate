package com.boilerplate.javaspringbootboilerplate.application.auth;

import com.boilerplate.javaspringbootboilerplate.application.exception.BoilerplateException;
import com.boilerplate.javaspringbootboilerplate.application.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;

    public Pair<String, String> generateTokenBy(LocalDateTime now, Long id) {
        return Pair.of(
            jwtUtil.generateAccessToken(now, id),
            jwtUtil.generateRefreshToken(now, id)
        );
    }

    public String reissue(LocalDateTime now, String refreshToken) {
        try {
            return jwtUtil.reissue(now, refreshToken);
        } catch (Exception ex) {
            throw new BoilerplateException(ErrorCode.UNAUTHORIZED);
        }
    }
}

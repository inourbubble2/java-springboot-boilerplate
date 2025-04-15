package com.boilerplate.javaspringbootboilerplate.application.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("jwt")
public class JwtProperty {
    private String accessTokenSecretKey;
    private Long accessTokenExpires;
    private String refreshTokenSecretKey;
    private Long refreshTokenExpires;
}

package com.boilerplate.javaspringbootboilerplate.presentation.auth;

public record TokenResponse(
    String accessToken,
    String refreshToken
) {
}

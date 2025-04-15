package com.boilerplate.javaspringbootboilerplate.presentation.auth;

import jakarta.validation.constraints.NotBlank;

public record IssueTokenRequest(
    @NotBlank String username,
    @NotBlank String password
) {

}

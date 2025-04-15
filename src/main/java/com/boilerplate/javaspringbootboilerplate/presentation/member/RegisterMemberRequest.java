package com.boilerplate.javaspringbootboilerplate.presentation.member;

import jakarta.validation.constraints.NotBlank;

public record RegisterMemberRequest(
    @NotBlank String username,
    @NotBlank String password
) {
}

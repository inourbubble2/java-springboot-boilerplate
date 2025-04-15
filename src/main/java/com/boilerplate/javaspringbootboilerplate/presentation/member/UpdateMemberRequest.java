package com.boilerplate.javaspringbootboilerplate.presentation.member;

import jakarta.validation.constraints.NotBlank;

public record UpdateMemberRequest(
    @NotBlank String password
) {
}

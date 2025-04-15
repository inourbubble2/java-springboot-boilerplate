package com.boilerplate.javaspringbootboilerplate.presentation.exception;

public record ErrorResponse(
    String code,
    String message
) {
}

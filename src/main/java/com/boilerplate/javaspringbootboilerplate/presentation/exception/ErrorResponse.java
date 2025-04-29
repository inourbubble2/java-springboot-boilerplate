package com.boilerplate.javaspringbootboilerplate.presentation.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record ErrorResponse(
    String code,
    String message
) {
}

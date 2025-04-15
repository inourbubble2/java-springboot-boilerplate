package com.boilerplate.javaspringbootboilerplate.application.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    BAD_REQUEST("Invalid Request", 400),
    ENTITY_NOT_FOUND("Requested Entity Not Found", 400),
    DUPLICATED_ENTITY("Duplicated Entity", 400),
    UNAUTHORIZED("Unauthenticated User", 401),
    FORBIDDEN("Unauthorized User", 403),
    INTERNAL_SERVER_ERROR("Unexpected Error", 500);

    private final String message;
    private final int status;
}

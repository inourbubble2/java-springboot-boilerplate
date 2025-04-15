package com.boilerplate.javaspringbootboilerplate.application.exception;

import lombok.Getter;

@Getter
public class BoilerplateException extends RuntimeException {
    private final String code;
    private final String message;
    private final int status;

    public BoilerplateException(String message, String code, int status) {
        super(message);
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public BoilerplateException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.name();
        this.message = errorCode.getMessage();
        this.status = errorCode.getStatus();
    }
}

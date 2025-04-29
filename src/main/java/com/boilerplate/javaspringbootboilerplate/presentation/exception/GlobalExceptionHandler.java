package com.boilerplate.javaspringbootboilerplate.presentation.exception;

import com.boilerplate.javaspringbootboilerplate.application.exception.BoilerplateException;
import com.boilerplate.javaspringbootboilerplate.application.exception.ErrorCode;
import lombok.val;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BoilerplateException.class)
    public ResponseEntity<ErrorResponse> boilerplateExceptionHandler(BoilerplateException ex) {
        return ResponseEntity.status(ex.getStatus())
            .body(new ErrorResponse(ex.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        val message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return ResponseEntity.status(400)
            .body(new ErrorResponse(ErrorCode.BAD_REQUEST.name(), message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        val message = e.getMessage();
        return ResponseEntity.status(500)
            .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.name(), message));
    }
}

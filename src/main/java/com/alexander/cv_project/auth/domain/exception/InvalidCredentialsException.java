package com.alexander.cv_project.auth.domain.exception;

public class InvalidCredentialsException extends ValidationException {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}

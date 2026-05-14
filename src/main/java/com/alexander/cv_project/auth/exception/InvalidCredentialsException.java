package com.alexander.cv_project.auth.exception;

public class InvalidCredentialsException extends ValidationException {
    public InvalidCredentialsException() {
        super("Invalid credentials");
    }
}

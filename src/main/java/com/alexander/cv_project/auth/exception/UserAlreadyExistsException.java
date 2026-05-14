package com.alexander.cv_project.auth.exception;

public class UserAlreadyExistsException extends ConflictException {
    public UserAlreadyExistsException(String email) {
        super("User already exists with email: " + email);
    }
}

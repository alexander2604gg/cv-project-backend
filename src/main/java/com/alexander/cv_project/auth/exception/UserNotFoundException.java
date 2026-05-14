package com.alexander.cv_project.auth.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String email) {
        super("User not found with email: " + email);
    }
}

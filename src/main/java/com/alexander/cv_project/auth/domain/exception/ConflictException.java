package com.alexander.cv_project.auth.domain.exception;

public class ConflictException extends DomainException {
    public ConflictException(String message) {
        super("CONFLICT", message);
    }
}

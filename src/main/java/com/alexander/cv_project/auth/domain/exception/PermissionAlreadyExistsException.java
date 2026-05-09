package com.alexander.cv_project.auth.domain.exception;

public class PermissionAlreadyExistsException extends ConflictException {
    public PermissionAlreadyExistsException() {
        super("Permission already exists");
    }
}

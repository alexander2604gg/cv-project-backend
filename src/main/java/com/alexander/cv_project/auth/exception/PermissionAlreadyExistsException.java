package com.alexander.cv_project.auth.exception;

public class PermissionAlreadyExistsException extends ConflictException {
    public PermissionAlreadyExistsException() {
        super("Permission already exists");
    }
}

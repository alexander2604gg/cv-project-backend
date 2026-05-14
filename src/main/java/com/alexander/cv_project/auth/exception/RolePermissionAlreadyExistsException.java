package com.alexander.cv_project.auth.exception;

public class RolePermissionAlreadyExistsException extends ConflictException {
    public RolePermissionAlreadyExistsException() {
        super("Role permission already exists");
    }
}

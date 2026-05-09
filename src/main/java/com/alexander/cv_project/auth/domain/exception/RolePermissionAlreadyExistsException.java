package com.alexander.cv_project.auth.domain.exception;

public class RolePermissionAlreadyExistsException extends ConflictException {
    public RolePermissionAlreadyExistsException() {
        super("Role permission already assigned");
    }
}

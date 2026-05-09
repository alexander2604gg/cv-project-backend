package com.alexander.cv_project.auth.domain.exception;

public class RoleAlreadyExistsException extends ConflictException {
    public RoleAlreadyExistsException() {
        super("Role already exists");
    }
}

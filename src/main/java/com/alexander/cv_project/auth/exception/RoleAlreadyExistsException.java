package com.alexander.cv_project.auth.exception;

public class RoleAlreadyExistsException extends ConflictException {
    public RoleAlreadyExistsException() {
        super("Role already exists");
    }
}

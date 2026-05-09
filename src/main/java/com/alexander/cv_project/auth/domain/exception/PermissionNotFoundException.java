package com.alexander.cv_project.auth.domain.exception;

public class PermissionNotFoundException extends ResourceNotFoundException {
    public PermissionNotFoundException(Long permissionId) {
        super("Permission not found with id: " + permissionId);
    }
}

package com.alexander.cv_project.auth.exception;

public class PermissionNotFoundException extends ResourceNotFoundException {
    public PermissionNotFoundException(Long permissionId) {
        super("Permission not found with id: " + permissionId);
    }
}

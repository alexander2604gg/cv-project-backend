package com.alexander.cv_project.auth.exception;

public class RoleNotFoundException extends ResourceNotFoundException {
    public RoleNotFoundException(Long roleId) {
        super("Role not found with id: " + roleId);
    }
}

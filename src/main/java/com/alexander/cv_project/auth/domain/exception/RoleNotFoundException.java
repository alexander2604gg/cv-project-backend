package com.alexander.cv_project.auth.domain.exception;

public class RoleNotFoundException extends ResourceNotFoundException {
    public RoleNotFoundException(Long roleId) {
        super("Role not found with id: " + roleId);
    }
}

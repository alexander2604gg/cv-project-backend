package com.alexander.cv_project.auth.application.service;

import com.alexander.cv_project.auth.application.port.in.DeletePermissionUseCase;
import com.alexander.cv_project.auth.domain.exception.PermissionNotFoundException;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DeletePermissionService implements DeletePermissionUseCase {

    private final PermissionRepositoryPort permissionRepositoryPort;

    @Override
    public void deletePermission(Long permissionId) {
        Permission permission = permissionRepositoryPort.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
        permissionRepositoryPort.delete(permission);
    }
}

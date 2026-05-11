package com.alexander.cv_project.auth.application.service;

import com.alexander.cv_project.auth.application.port.in.DeletePermissionUseCase;
import com.alexander.cv_project.auth.domain.exception.PermissionNotFoundException;
import com.alexander.cv_project.auth.domain.exception.RolePermissionAlreadyExistsException;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RolePermissionRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeletePermissionService implements DeletePermissionUseCase {

    private final PermissionRepositoryPort permissionRepositoryPort;
    private final RolePermissionRepositoryPort rolePermissionRepositoryPort;

    @Override
    public void execute(Long permissionId) {
        List<RolePermission> rolePermissions = rolePermissionRepositoryPort.findByPermissionId(permissionId);

        if (rolePermissions != null && rolePermissions.isEmpty()){
            Permission permission = permissionRepositoryPort.findById(permissionId)
                    .orElseThrow(() -> new PermissionNotFoundException(permissionId));
            permissionRepositoryPort.delete(permission);
        } else {
            throw new RolePermissionAlreadyExistsException();
        }



    }
}

package com.alexander.cv_project.auth.application.service;

import com.alexander.cv_project.auth.application.port.in.DeleteRoleUseCase;
import com.alexander.cv_project.auth.domain.exception.RoleNotFoundException;
import com.alexander.cv_project.auth.domain.exception.RolePermissionAlreadyExistsException;
import com.alexander.cv_project.auth.domain.model.Role;
import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.domain.port.out.RolePermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RoleRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DeleteRolService implements DeleteRoleUseCase {

    private final RoleRepositoryPort roleRepositoryPort;
    private final RolePermissionRepositoryPort rolePermissionRepositoryPort;

    @Override
    public void execute(Long roleId) {
        List<RolePermission> rolePermissions = rolePermissionRepositoryPort.findAllByRoleId(roleId);
        if (rolePermissions != null && rolePermissions.isEmpty()){
            Role role = roleRepositoryPort.findById(roleId).orElseThrow(() -> new RoleNotFoundException(roleId));
            roleRepositoryPort.delete(role);
        } else {
            throw new RolePermissionAlreadyExistsException();
        }
    }

}

package com.alexander.cv_project.auth.application.service;

import com.alexander.cv_project.auth.application.port.in.ListPermissionsByRoleUseCase;
import com.alexander.cv_project.auth.domain.port.out.RolePermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.model.RolePermission;

import java.util.List;

public class ListPermissionsByRoleService implements ListPermissionsByRoleUseCase {
    private final RolePermissionRepositoryPort rolePermissionRepositoryPort;

    public ListPermissionsByRoleService(RolePermissionRepositoryPort rolePermissionRepositoryPort) {
        this.rolePermissionRepositoryPort = rolePermissionRepositoryPort;
    }

    @Override
    public List<RolePermission> execute(Long roleId) {
        return rolePermissionRepositoryPort.findAllByRoleId(roleId);
    }
}

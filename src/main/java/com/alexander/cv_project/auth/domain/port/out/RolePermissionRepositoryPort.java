package com.alexander.cv_project.auth.domain.port.out;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.RolePermission;

public interface RolePermissionRepositoryPort {
    RolePermission save(RolePermission rolePermission);

    void delete(RolePermission rolePermission);

    List<RolePermission> findAllByRoleId(Long roleId);

    List<RolePermission> findByPermissionId(Long permissionId);
}

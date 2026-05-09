package com.alexander.cv_project.auth.application.port.in;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.RolePermission;

public interface AssignRolePermissionsUseCase {
    List<RolePermission> execute(Long roleId, List<Long> permissionIds);
}

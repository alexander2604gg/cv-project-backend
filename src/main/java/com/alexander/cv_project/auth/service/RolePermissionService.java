package com.alexander.cv_project.auth.service;

import java.util.List;

import com.alexander.cv_project.auth.dto.AssignRolePermissionsRequest;
import com.alexander.cv_project.auth.dto.RolePermissionResponse;

public interface RolePermissionService {
    List<RolePermissionResponse> assignPermissions(AssignRolePermissionsRequest request);
    List<RolePermissionResponse> findByRoleId (Long roleId);
}

package com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.RolePermissionResponse;

public class RolePermissionWebMapper {

    public static RolePermissionResponse toResponse(RolePermission rolePermission) {
        return RolePermissionResponse.builder()
                .roleId(rolePermission.getRoleId())
                .permissionId(rolePermission.getPermissionId())
                .assignedAt(rolePermission.getAssignedAt())
                .active(rolePermission.isActive())
                .build();
    }

    public static List<RolePermissionResponse> toResponseList(List<RolePermission> rolePermissions) {
        if (rolePermissions == null) {
            return List.of();
        }

        return rolePermissions.stream().map(RolePermissionWebMapper::toResponse).toList();
    }
}

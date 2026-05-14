package com.alexander.cv_project.auth.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alexander.cv_project.auth.dto.RolePermissionResponse;
import com.alexander.cv_project.auth.entity.RolePermissionEntity;

@Component
public class RolePermissionMapper {

    public RolePermissionResponse toResponse(RolePermissionEntity entity) {
        return RolePermissionResponse.builder()
                .roleId(entity.getId().getRoleId())
                .permissionId(entity.getId().getPermissionId())
                .assignedAt(entity.getAssignedAt())
                .active(entity.isActive())
                .build();
    }

    public List<RolePermissionResponse> toResponseList(List<RolePermissionEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toResponse).toList();
    }
}

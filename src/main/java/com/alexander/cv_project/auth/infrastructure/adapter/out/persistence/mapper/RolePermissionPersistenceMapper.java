package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.mapper;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.PermissionEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RoleEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RolePermissionEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RolePermissionId;

public class RolePermissionPersistenceMapper {

    public static RolePermissionEntity toEntity(RolePermission domain) {
        RolePermissionId id = new RolePermissionId(domain.getRoleId(), domain.getPermissionId());

        return RolePermissionEntity.builder()
                .id(id)
                .role(RoleEntity.builder().id(domain.getRoleId()).build())
                .permission(PermissionEntity.builder().id(domain.getPermissionId()).build())
                .assignedAt(domain.getAssignedAt())
                .active(domain.isActive())
                .build();
    }

    public static RolePermission toDomain(RolePermissionEntity entity) {
        return RolePermission.builder()
                .roleId(entity.getId().getRoleId())
                .permissionId(entity.getId().getPermissionId())
                .assignedAt(entity.getAssignedAt())
                .active(entity.isActive())
                .build();
    }

    public static List<RolePermission> toDomainList(List<RolePermissionEntity> entities) {
        if (entities == null) {
            return List.of();
        }

        return entities.stream().map(RolePermissionPersistenceMapper::toDomain).toList();
    }
}

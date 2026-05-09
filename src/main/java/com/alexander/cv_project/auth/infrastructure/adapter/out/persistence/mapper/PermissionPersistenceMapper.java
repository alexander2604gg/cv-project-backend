package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.mapper;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.PermissionEntity;

public class PermissionPersistenceMapper {

    public static PermissionEntity toEntity(Permission domain) {
        return PermissionEntity.builder()
                .id(domain.getId())
                .code(domain.getCode())
                .name(domain.getName())
                .description(domain.getDescription())
                .build();
    }

    public static Permission toDomain(PermissionEntity entity) {
        return new Permission(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription());
    }

    public static List<PermissionEntity> toEntityList(List<Permission> domains) {
        if (domains == null)
            return List.of();

        return domains.stream()
                .map(PermissionPersistenceMapper::toEntity)
                .toList();
    }

    public static List<Permission> toDomainList(List<PermissionEntity> entities) {
        if (entities == null)
            return List.of();

        return entities.stream()
                .map(PermissionPersistenceMapper::toDomain)
                .toList();
    }
}

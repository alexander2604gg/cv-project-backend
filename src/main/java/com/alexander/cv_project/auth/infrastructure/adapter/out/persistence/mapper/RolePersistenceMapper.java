package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.mapper;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.Role;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RoleEntity;

public class RolePersistenceMapper {

    public static RoleEntity toEntity(Role domain) {
        return RoleEntity.builder()
                .id(domain.getId())
                .code(domain.getCode())
                .name(domain.getName())
                .description(domain.getDescription())
                .build();
    }

    public static Role toDomain(RoleEntity entity) {
        return new Role(
                entity.getId(),
                entity.getCode(),
                entity.getName(),
                entity.getDescription());
    }

    public static List<Role> toDomainList(List<RoleEntity> entities) {
        if (entities == null) {
            return List.of();
        }

        return entities.stream().map(RolePersistenceMapper::toDomain).toList();
    }
}

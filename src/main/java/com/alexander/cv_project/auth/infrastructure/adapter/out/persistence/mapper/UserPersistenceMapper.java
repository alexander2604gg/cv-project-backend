package com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.mapper;

import java.util.HashSet;
import java.util.Set;

import com.alexander.cv_project.auth.domain.model.Role;
import com.alexander.cv_project.auth.domain.model.User;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.RoleEntity;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.entity.UserEntity;

public class UserPersistenceMapper {

    public static UserEntity toEntity(User domain) {
        return UserEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .roles(toRoleEntitySet(domain.getRoles()))
                .build();
    }

    public static User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .roles(toRoleDomainSet(entity.getRoles()))
                .build();
    }

    private static Set<RoleEntity> toRoleEntitySet(Set<Role> roles) {
        if (roles == null) {
            return new HashSet<>();
        }

        return roles.stream()
                .map(role -> RoleEntity.builder()
                        .id(role.getId())
                        .code(role.getCode())
                        .name(role.getName())
                        .description(role.getDescription())
                        .build())
                .collect(java.util.stream.Collectors.toSet());
    }

    private static Set<Role> toRoleDomainSet(Set<RoleEntity> roleEntities) {
        if (roleEntities == null) {
            return new HashSet<>();
        }

        return roleEntities.stream()
                .map(roleEntity -> new Role(
                        roleEntity.getId(),
                        roleEntity.getCode(),
                        roleEntity.getName(),
                        roleEntity.getDescription()))
                .collect(java.util.stream.Collectors.toSet());
    }
}

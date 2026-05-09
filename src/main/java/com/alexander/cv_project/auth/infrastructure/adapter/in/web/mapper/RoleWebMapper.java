package com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.Role;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.RoleRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.RoleResponse;

public class RoleWebMapper {

    public static Role toDomain(RoleRequest request) {
        return new Role(
                null,
                request.getCode(),
                request.getName(),
                request.getDescription());
    }

    public static RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .code(role.getCode())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }

    public static List<RoleResponse> toResponseList(List<Role> roles) {
        if (roles == null) {
            return List.of();
        }

        return roles.stream().map(RoleWebMapper::toResponse).toList();
    }
}

package com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper;

import java.util.List;

import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.PermissionRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.PermissionResponse;

public class PermissionWebMapper {

    public static Permission toDomain(PermissionRequest request) {
        return Permission.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public static PermissionResponse toResponse(Permission permission) {
        return new PermissionResponse(
                permission.getId(),
                permission.getCode(),
                permission.getName(),
                permission.getDescription());
    }

    public static List<PermissionResponse> toResponseList(List<Permission> permissions) {
        if (permissions == null) {
            return List.of();
        }

        return permissions.stream()
                .map(PermissionWebMapper::toResponse)
                .toList();
    }
}

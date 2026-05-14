package com.alexander.cv_project.auth.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alexander.cv_project.auth.dto.PermissionRequest;
import com.alexander.cv_project.auth.dto.PermissionResponse;
import com.alexander.cv_project.auth.entity.PermissionEntity;

@Component
public class PermissionMapper {

    public PermissionEntity toEntity(PermissionRequest request) {
        return PermissionEntity.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public PermissionResponse toResponse(PermissionEntity entity) {
        return PermissionResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public List<PermissionResponse> toResponseList(List<PermissionEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toResponse).toList();
    }
}

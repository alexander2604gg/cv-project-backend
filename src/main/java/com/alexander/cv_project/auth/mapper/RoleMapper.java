package com.alexander.cv_project.auth.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.alexander.cv_project.auth.dto.RoleRequest;
import com.alexander.cv_project.auth.dto.RoleResponse;
import com.alexander.cv_project.auth.entity.RoleEntity;

@Component
public class RoleMapper {

    public RoleEntity toEntity(RoleRequest request) {
        return RoleEntity.builder()
                .code(request.getCode())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public RoleResponse toResponse(RoleEntity entity) {
        return RoleResponse.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public List<RoleResponse> toResponseList(List<RoleEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream().map(this::toResponse).toList();
    }
}

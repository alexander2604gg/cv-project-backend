package com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolePermissionResponse {
    private Long roleId;
    private Long permissionId;
    private LocalDateTime assignedAt;
    private boolean active;
}

package com.alexander.cv_project.auth.domain.model;

import java.time.LocalDateTime;

import com.alexander.cv_project.auth.domain.exception.ValidationException;

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
public class RolePermission {

    private Long id;

    private Long roleId;
    private Long permissionId;

    private LocalDateTime assignedAt;
    private boolean active;

    public RolePermission(Long roleId, Long permissionId) {
        validate(roleId, permissionId);
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.assignedAt = LocalDateTime.now();
        this.active = true;
    }

    private void validate(Long roleId, Long permissionId) {
        if (roleId == null) {
            throw new ValidationException("RoleId is required");
        }
        if (permissionId == null) {
            throw new ValidationException("PermissionId is required");
        }
    }

    public void deactivate() {
        this.active = false;
    }
}

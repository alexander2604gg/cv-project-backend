package com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto;

import java.util.List;

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
public class AssignRolePermissionsRequest {
    private Long roleId;
    private List<Long> permissionIds;
}

package com.alexander.cv_project.auth.infrastructure.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.cv_project.auth.application.port.in.AssignRolePermissionsUseCase;
import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.AssignRolePermissionsRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.RolePermissionResponse;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper.RolePermissionWebMapper;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    private final AssignRolePermissionsUseCase useCase;

    public RolePermissionController(AssignRolePermissionsUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/assign")
    public ResponseEntity<List<RolePermissionResponse>> assign(@RequestBody AssignRolePermissionsRequest request) {
        List<RolePermission> assigned = useCase.execute(request.getRoleId(), request.getPermissionIds());
        return ResponseEntity.ok(RolePermissionWebMapper.toResponseList(assigned));
    }
}

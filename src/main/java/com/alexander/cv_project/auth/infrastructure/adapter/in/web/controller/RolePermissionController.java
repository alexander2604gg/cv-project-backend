package com.alexander.cv_project.auth.infrastructure.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.cv_project.auth.application.port.in.AssignRolePermissionsUseCase;
import com.alexander.cv_project.auth.application.port.in.ListPermissionsByRoleUseCase;
import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.AssignRolePermissionsRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.RolePermissionResponse;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper.RolePermissionWebMapper;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    private final AssignRolePermissionsUseCase assignRolePermissionsUseCase;
    private final ListPermissionsByRoleUseCase listPermissionsByRoleUseCase;

    public RolePermissionController(AssignRolePermissionsUseCase assignRolePermissionsUseCase, ListPermissionsByRoleUseCase listPermissionsByRoleUseCase) {
        this.assignRolePermissionsUseCase = assignRolePermissionsUseCase;
        this.listPermissionsByRoleUseCase = listPermissionsByRoleUseCase;
    }

    @PostMapping("/assign")
    public ResponseEntity<List<RolePermissionResponse>> assign(@RequestBody AssignRolePermissionsRequest request) {
        List<RolePermission> assigned = assignRolePermissionsUseCase.execute(request.getRoleId(), request.getPermissionIds());
        return ResponseEntity.ok(RolePermissionWebMapper.toResponseList(assigned));
    }

    @GetMapping("/find-by-role-id")
    public ResponseEntity<List<RolePermissionResponse>> findByRoleId(@RequestParam Long roleId) {
        List<RolePermission> rolePermissions = listPermissionsByRoleUseCase.execute(roleId);
        return ResponseEntity.ok(RolePermissionWebMapper.toResponseList(rolePermissions));
    }
}

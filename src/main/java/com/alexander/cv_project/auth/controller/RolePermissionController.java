package com.alexander.cv_project.auth.controller;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alexander.cv_project.auth.dto.AssignRolePermissionsRequest;
import com.alexander.cv_project.auth.dto.RolePermissionResponse;
import com.alexander.cv_project.auth.service.RolePermissionService;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    public RolePermissionController(RolePermissionService rolePermissionService) {
        this.rolePermissionService = rolePermissionService;
    }

    @PostMapping("/assign")
    public ResponseEntity<List<RolePermissionResponse>> assign(@RequestBody AssignRolePermissionsRequest request) {
        return ResponseEntity.ok(rolePermissionService.assignPermissions(request));
    }

    @GetMapping("/find-by-role-id/{roleId}")
    public ResponseEntity<List<RolePermissionResponse>> findByRoleId(@PathVariable Long roleId) {
        return ResponseEntity.ok(rolePermissionService.findByRoleId(roleId));
    }
}

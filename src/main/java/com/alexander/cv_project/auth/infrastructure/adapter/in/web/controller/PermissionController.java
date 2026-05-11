package com.alexander.cv_project.auth.infrastructure.adapter.in.web.controller;

import java.util.List;

import com.alexander.cv_project.auth.application.port.in.DeletePermissionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.application.port.in.ListPermissionsUseCase;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.PermissionRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.PermissionResponse;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper.PermissionWebMapper;
import com.alexander.cv_project.auth.application.port.in.CreatePermissionUseCase;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final CreatePermissionUseCase createPermissionUseCase;
    private final ListPermissionsUseCase listPermissionsUseCase;
    private final DeletePermissionUseCase deletePermissionUseCase;

    @PostMapping
    public ResponseEntity<PermissionResponse> create(@RequestBody PermissionRequest permissionRequest) {
        Permission permission = PermissionWebMapper.toDomain(permissionRequest);
        Permission saved = createPermissionUseCase.execute(permission);
        return ResponseEntity.ok(PermissionWebMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> list() {
        List<PermissionResponse> permissions = PermissionWebMapper.toResponseList(listPermissionsUseCase.execute());
        return ResponseEntity.ok(permissions);
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> delete(@PathVariable Long permissionId) {
        deletePermissionUseCase.deletePermission(permissionId);
        return ResponseEntity.noContent().build();
    }
}

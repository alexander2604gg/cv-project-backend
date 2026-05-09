package com.alexander.cv_project.auth.infrastructure.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.application.port.in.ListPermissionsUseCase;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.PermissionRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.PermissionResponse;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper.PermissionWebMapper;
import com.alexander.cv_project.auth.application.port.in.CreatePermissionUseCase;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    private final CreatePermissionUseCase useCase;
    private final ListPermissionsUseCase listPermissionsUseCase;

    public PermissionController(CreatePermissionUseCase useCase, ListPermissionsUseCase listPermissionsUseCase) {
        this.useCase = useCase;
        this.listPermissionsUseCase = listPermissionsUseCase;
    }

    @PostMapping
    public ResponseEntity<PermissionResponse> create(@RequestBody PermissionRequest permissionRequest) {
        Permission permission = PermissionWebMapper.toDomain(permissionRequest);
        Permission saved = useCase.execute(permission);
        return ResponseEntity.ok(PermissionWebMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> list() {
        List<PermissionResponse> permissions = PermissionWebMapper.toResponseList(listPermissionsUseCase.execute());
        return ResponseEntity.ok(permissions);
    }
}

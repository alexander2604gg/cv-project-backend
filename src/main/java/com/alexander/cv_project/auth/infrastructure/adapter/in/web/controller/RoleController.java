package com.alexander.cv_project.auth.infrastructure.adapter.in.web.controller;

import java.util.List;

import com.alexander.cv_project.auth.application.port.in.DeleteRoleUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.alexander.cv_project.auth.application.port.in.CreateRoleUseCase;
import com.alexander.cv_project.auth.application.port.in.ListRolesUseCase;
import com.alexander.cv_project.auth.domain.model.Role;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.RoleRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.RoleResponse;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper.RoleWebMapper;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final CreateRoleUseCase useCase;
    private final ListRolesUseCase listRolesUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;


    @PostMapping
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest roleRequest) {
        Role role = RoleWebMapper.toDomain(roleRequest);
        Role saved = useCase.execute(role);
        return ResponseEntity.ok(RoleWebMapper.toResponse(saved));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> list() {
        List<RoleResponse> roles = RoleWebMapper.toResponseList(listRolesUseCase.execute());
        return ResponseEntity.ok(roles);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> delete(@PathVariable Long permissionId) {
        deleteRoleUseCase.execute(permissionId);
        return ResponseEntity.noContent().build();
    }
}

package com.alexander.cv_project.auth.infrastructure.adapter.in.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexander.cv_project.auth.application.port.in.CreateRoleUseCase;
import com.alexander.cv_project.auth.application.port.in.ListRolesUseCase;
import com.alexander.cv_project.auth.domain.model.Role;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.RoleRequest;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.dto.RoleResponse;
import com.alexander.cv_project.auth.infrastructure.adapter.in.web.mapper.RoleWebMapper;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final CreateRoleUseCase useCase;
    private final ListRolesUseCase listRolesUseCase;

    public RoleController(CreateRoleUseCase useCase, ListRolesUseCase listRolesUseCase) {
        this.useCase = useCase;
        this.listRolesUseCase = listRolesUseCase;
    }

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
}

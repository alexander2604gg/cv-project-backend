package com.alexander.cv_project.auth.application.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.alexander.cv_project.auth.application.port.in.RefreshAccessTokenUseCase;
import com.alexander.cv_project.auth.domain.model.AuthToken;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.domain.model.RefreshToken;
import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.domain.model.User;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RefreshTokenServicePort;
import com.alexander.cv_project.auth.domain.port.out.RolePermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.TokenServicePort;

public class RefreshAccessTokenService implements RefreshAccessTokenUseCase {

    private final RefreshTokenServicePort refreshTokenService;
    private final TokenServicePort tokenService;
    private final RolePermissionRepositoryPort rolePermissionRepository;
    private final PermissionRepositoryPort permissionRepository;

    public RefreshAccessTokenService(
            RefreshTokenServicePort refreshTokenService,
            TokenServicePort tokenService,
            RolePermissionRepositoryPort rolePermissionRepository,
            PermissionRepositoryPort permissionRepository) {
        this.refreshTokenService = refreshTokenService;
        this.tokenService = tokenService;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public AuthToken execute(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenService.validate(refreshTokenValue);
        User user = refreshToken.getUser();

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getCode())
                .collect(Collectors.toSet());

        Set<Long> permissionIds = user.getRoles().stream()
                .flatMap(role -> rolePermissionRepository.findAllByRoleId(role.getId()).stream())
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toSet());

        Set<String> permissions = permissionRepository.findAll().stream()
                .filter(permission -> permissionIds.contains(permission.getId()))
                .map(Permission::getCode)
                .collect(Collectors.toSet());

        String accessToken = tokenService.generateToken(user.getEmail(), roles, permissions);

        return AuthToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }
}

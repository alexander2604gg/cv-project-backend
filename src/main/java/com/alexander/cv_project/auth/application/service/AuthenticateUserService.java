package com.alexander.cv_project.auth.application.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.alexander.cv_project.auth.application.port.in.AuthenticateUserUseCase;
import com.alexander.cv_project.auth.domain.exception.InvalidCredentialsException;
import com.alexander.cv_project.auth.domain.model.AuthToken;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.domain.model.RefreshToken;
import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.domain.model.User;
import com.alexander.cv_project.auth.domain.port.out.PasswordEncoderPort;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RefreshTokenServicePort;
import com.alexander.cv_project.auth.domain.port.out.RolePermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.TokenServicePort;
import com.alexander.cv_project.auth.domain.port.out.UserRepositoryPort;

public class AuthenticateUserService implements AuthenticateUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenServicePort tokenService;
    private final RefreshTokenServicePort refreshTokenService;
    private final RolePermissionRepositoryPort rolePermissionRepository;
    private final PermissionRepositoryPort permissionRepository;

    public AuthenticateUserService(
            UserRepositoryPort userRepository,
            PasswordEncoderPort passwordEncoder,
            TokenServicePort tokenService,
            RefreshTokenServicePort refreshTokenService,
            RolePermissionRepositoryPort rolePermissionRepository,
            PermissionRepositoryPort permissionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public AuthToken execute(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getCode())
                .collect(Collectors.toSet());

        Set<Long> permissionIds = user.getRoles().stream()
                .flatMap(role -> rolePermissionRepository.findByRoleId(role.getId()).stream())
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toSet());

        Set<String> permissions = permissionRepository.findAll().stream()
                .filter(permission -> permissionIds.contains(permission.getId()))
                .map(Permission::getCode)
                .collect(Collectors.toSet());

        String jwt = tokenService.generateToken(user.getEmail(), roles, permissions);
        RefreshToken refreshToken = refreshTokenService.create(user);

        return AuthToken.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }
}

package com.alexander.cv_project.auth.application.service;

import java.util.Set;
import java.util.stream.Collectors;

import com.alexander.cv_project.auth.application.dto.UserProfileResponse;
import com.alexander.cv_project.auth.application.port.in.GetAuthenticatedUserProfileUseCase;
import com.alexander.cv_project.auth.domain.exception.UserNotFoundException;
import com.alexander.cv_project.auth.domain.model.Permission;
import com.alexander.cv_project.auth.domain.model.RolePermission;
import com.alexander.cv_project.auth.domain.model.User;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RolePermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.SecurityProviderPort;
import com.alexander.cv_project.auth.domain.port.out.UserRepositoryPort;

public class GetAuthenticatedUserProfileService implements GetAuthenticatedUserProfileUseCase {

    private final SecurityProviderPort securityProvider;
    private final UserRepositoryPort userRepository;
    private final RolePermissionRepositoryPort rolePermissionRepository;
    private final PermissionRepositoryPort permissionRepository;

    public GetAuthenticatedUserProfileService(
            SecurityProviderPort securityProvider,
            UserRepositoryPort userRepository,
            RolePermissionRepositoryPort rolePermissionRepository,
            PermissionRepositoryPort permissionRepository) {
        this.securityProvider = securityProvider;
        this.userRepository = userRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public UserProfileResponse execute() {
        String email = securityProvider.getAuthenticatedEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

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

        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(roles)
                .permissions(permissions)
                .build();
    }
}

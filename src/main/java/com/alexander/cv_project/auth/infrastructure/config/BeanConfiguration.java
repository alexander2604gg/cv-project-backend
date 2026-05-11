package com.alexander.cv_project.auth.infrastructure.config;

import com.alexander.cv_project.auth.application.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import com.alexander.cv_project.auth.application.port.in.ListPermissionsByRoleUseCase;
import com.alexander.cv_project.auth.domain.port.out.PasswordEncoderPort;
import com.alexander.cv_project.auth.domain.port.out.PermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RefreshTokenServicePort;
import com.alexander.cv_project.auth.domain.port.out.RolePermissionRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.RoleRepositoryPort;
import com.alexander.cv_project.auth.domain.port.out.SecurityProviderPort;
import com.alexander.cv_project.auth.domain.port.out.TokenServicePort;
import com.alexander.cv_project.auth.domain.port.out.UserRepositoryPort;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.RefreshTokenPersistenceAdapter;
import com.alexander.cv_project.auth.infrastructure.adapter.out.persistence.repository.SpringDataRefreshTokenRepo;
import com.alexander.cv_project.auth.infrastructure.adapter.out.security.JwtTokenServiceAdapter;
import com.alexander.cv_project.auth.infrastructure.adapter.out.security.SpringSecurityProviderAdapter;

@Configuration
public class BeanConfiguration {

    @Bean
    public DeletePermissionService deletePermissionService (PermissionRepositoryPort permissionRepositoryPort){
        return new DeletePermissionService(permissionRepositoryPort);
    }

    @Bean
    public CreatePermissionService createPermissionService(PermissionRepositoryPort permissionRepositoryPort) {
        return new CreatePermissionService(permissionRepositoryPort);
    }

    @Bean
    public ListPermissionsService listPermissionsService(PermissionRepositoryPort permissionRepositoryPort) {
        return new ListPermissionsService(permissionRepositoryPort);
    }

    @Bean
    public CreateRoleService createRoleService(RoleRepositoryPort roleRepositoryPort) {
        return new CreateRoleService(roleRepositoryPort);
    }

    @Bean
    public ListRolesService listRolesService(RoleRepositoryPort roleRepositoryPort) {
        return new ListRolesService(roleRepositoryPort);
    }

    @Bean
    public AssignRolePermissionsService assignRolePermissionsService(
            RolePermissionRepositoryPort rolePermissionRepositoryPort,
            RoleRepositoryPort roleRepositoryPort,
            PermissionRepositoryPort permissionRepositoryPort) {
        return new AssignRolePermissionsService(
                rolePermissionRepositoryPort,
                roleRepositoryPort,
                permissionRepositoryPort);
    }

    @Bean
    public RegisterUserService registerUserService(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort) {
        return new RegisterUserService(userRepositoryPort, passwordEncoderPort);
    }

    @Bean
    public TokenServicePort tokenServicePort(
            @Value("${security.jwt.secret}") String jwtSecret,
            @Value("${security.jwt.expiration-ms}") long jwtExpirationMs) {
        return new JwtTokenServiceAdapter(jwtSecret, jwtExpirationMs);
    }

    @Bean
    public RefreshTokenServicePort refreshTokenServicePort(
            SpringDataRefreshTokenRepo springDataRefreshTokenRepo,
            @Value("${security.refresh-token.expiration-ms}") long refreshTokenExpirationMs) {
        return new RefreshTokenPersistenceAdapter(springDataRefreshTokenRepo, refreshTokenExpirationMs);
    }

    @Bean
    public SecurityProviderPort securityProviderPort() {
        return new SpringSecurityProviderAdapter();
    }

    @Bean
    public AuthenticateUserService authenticateUserService(
            UserRepositoryPort userRepositoryPort,
            PasswordEncoderPort passwordEncoderPort,
            TokenServicePort tokenServicePort,
            RefreshTokenServicePort refreshTokenServicePort,
            RolePermissionRepositoryPort rolePermissionRepositoryPort,
            PermissionRepositoryPort permissionRepositoryPort) {
        return new AuthenticateUserService(
                userRepositoryPort,
                passwordEncoderPort,
                tokenServicePort,
                refreshTokenServicePort,
                rolePermissionRepositoryPort,
                permissionRepositoryPort);
    }

    @Bean
    public RefreshAccessTokenService refreshAccessTokenService(
            RefreshTokenServicePort refreshTokenServicePort,
            TokenServicePort tokenServicePort,
            RolePermissionRepositoryPort rolePermissionRepositoryPort,
            PermissionRepositoryPort permissionRepositoryPort) {
        return new RefreshAccessTokenService(
                refreshTokenServicePort,
                tokenServicePort,
                rolePermissionRepositoryPort,
                permissionRepositoryPort);
    }

    @Bean
    public GetAuthenticatedUserProfileService getAuthenticatedUserProfileService(
            SecurityProviderPort securityProviderPort,
            UserRepositoryPort userRepositoryPort,
            RolePermissionRepositoryPort rolePermissionRepositoryPort,
            PermissionRepositoryPort permissionRepositoryPort) {
        return new GetAuthenticatedUserProfileService(
                securityProviderPort,
                userRepositoryPort,
                rolePermissionRepositoryPort,
                permissionRepositoryPort);
    }

    @Bean
    public ListPermissionsByRoleService listPermissionsByRoleUseCase(
            RolePermissionRepositoryPort rolePermissionRepositoryPort) {
        return new ListPermissionsByRoleService(rolePermissionRepositoryPort);
    }

}

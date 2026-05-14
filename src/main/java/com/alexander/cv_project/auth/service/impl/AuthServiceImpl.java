package com.alexander.cv_project.auth.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexander.cv_project.auth.exception.InvalidCredentialsException;
import com.alexander.cv_project.auth.exception.UserAlreadyExistsException;
import com.alexander.cv_project.auth.exception.UserNotFoundException;
import com.alexander.cv_project.auth.exception.ValidationException;
import com.alexander.cv_project.auth.dto.AuthTokenResponse;
import com.alexander.cv_project.auth.dto.LoginRequest;
import com.alexander.cv_project.auth.dto.RefreshTokenRequest;
import com.alexander.cv_project.auth.dto.UserProfileResponse;
import com.alexander.cv_project.auth.dto.UserRegistrationRequest;
import com.alexander.cv_project.auth.dto.UserResponse;
import com.alexander.cv_project.auth.entity.RefreshTokenEntity;
import com.alexander.cv_project.auth.entity.RolePermissionEntity;
import com.alexander.cv_project.auth.entity.UserEntity;
import com.alexander.cv_project.auth.mapper.UserMapper;
import com.alexander.cv_project.auth.repository.PermissionRepository;
import com.alexander.cv_project.auth.repository.RefreshTokenRepository;
import com.alexander.cv_project.auth.repository.RolePermissionRepository;
import com.alexander.cv_project.auth.repository.UserRepository;
import com.alexander.cv_project.auth.service.AuthService;
import com.alexander.cv_project.auth.service.TokenService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final long refreshTokenExpirationMs;

    public AuthServiceImpl(
            UserRepository userRepository,
            RolePermissionRepository rolePermissionRepository,
            PermissionRepository permissionRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            TokenService tokenService,
            UserMapper userMapper,
            @Value("${security.refresh-token.expiration-ms}") long refreshTokenExpirationMs) {
        this.userRepository = userRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.userMapper = userMapper;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    @Override
    @Transactional
    public UserResponse register(UserRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(request.getEmail());
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserEntity saved = userRepository.save(userMapper.toEntity(request, encodedPassword));
        return userMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public AuthTokenResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getCode())
                .collect(Collectors.toSet());

        Set<Long> permissionIds = user.getRoles().stream()
                .flatMap(role -> rolePermissionRepository.findByRoleId(role.getId()).stream())
                .map(RolePermissionEntity::getId)
                .map(id -> id.getPermissionId())
                .collect(Collectors.toSet());

        Set<String> permissions = permissionRepository.findAllById(permissionIds).stream()
                .map(permission -> permission.getCode())
                .collect(Collectors.toSet());

        String accessToken = tokenService.generateToken(user.getEmail(), roles, permissions);
        RefreshTokenEntity refreshToken = createOrReplaceRefreshToken(user);

        return AuthTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    @Override
    @Transactional
    public AuthTokenResponse refresh(RefreshTokenRequest request) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new ValidationException("Refresh token not found"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Refresh token expired");
        }

        UserEntity user = refreshToken.getUser();
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getCode())
                .collect(Collectors.toSet());

        Set<Long> permissionIds = user.getRoles().stream()
                .flatMap(role -> rolePermissionRepository.findByRoleId(role.getId()).stream())
                .map(RolePermissionEntity::getId)
                .map(id -> id.getPermissionId())
                .collect(Collectors.toSet());

        Set<String> permissions = permissionRepository.findAllById(permissionIds).stream()
                .map(permission -> permission.getCode())
                .collect(Collectors.toSet());

        String accessToken = tokenService.generateToken(user.getEmail(), roles, permissions);

        return AuthTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .tokenType("Bearer")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileResponse me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
            throw new ValidationException("Authenticated user not found");
        }

        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getCode())
                .collect(Collectors.toSet());

        Set<Long> permissionIds = user.getRoles().stream()
                .flatMap(role -> rolePermissionRepository.findByRoleId(role.getId()).stream())
                .map(RolePermissionEntity::getId)
                .map(id -> id.getPermissionId())
                .collect(Collectors.toSet());

        Set<String> permissions = permissionRepository.findAllById(permissionIds).stream()
                .map(permission -> permission.getCode())
                .collect(Collectors.toSet());

        return UserProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(roles)
                .permissions(permissions)
                .build();
    }

    private RefreshTokenEntity createOrReplaceRefreshToken(UserEntity user) {
        refreshTokenRepository.deleteByUser_Id(user.getId());

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now().plus(Duration.ofMillis(refreshTokenExpirationMs)))
                .user(user)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }
}

package com.alexander.cv_project.auth.service;

import java.util.List;

import com.alexander.cv_project.auth.dto.PermissionRequest;
import com.alexander.cv_project.auth.dto.PermissionResponse;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> list();

    void delete(Long permissionId);
}

package com.alexander.cv_project.auth.service;

import java.util.List;

import com.alexander.cv_project.auth.dto.RoleRequest;
import com.alexander.cv_project.auth.dto.RoleResponse;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> list();

    void delete(Long roleId);
}

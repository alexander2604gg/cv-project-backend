package com.alexander.cv_project.auth.application.port.in;

import com.alexander.cv_project.auth.domain.model.Permission;

public interface CreatePermissionUseCase {
    Permission execute(Permission permission);
}

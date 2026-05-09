package com.alexander.cv_project.auth.infrastructure.adapter.out.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.alexander.cv_project.auth.domain.exception.ValidationException;
import com.alexander.cv_project.auth.domain.port.out.SecurityProviderPort;

public class SpringSecurityProviderAdapter implements SecurityProviderPort {

    @Override
    public String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getName() == null) {
            throw new ValidationException("Authenticated user not found");
        }

        return authentication.getName();
    }
}

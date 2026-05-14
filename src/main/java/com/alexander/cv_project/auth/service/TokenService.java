package com.alexander.cv_project.auth.service;

import java.util.Set;

public interface TokenService {
    String generateToken(String email, Set<String> roles, Set<String> permissions);

    String extractUsername(String token);

    boolean isTokenValid(String token);
}

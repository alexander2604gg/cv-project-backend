package com.alexander.cv_project.auth.domain.port.out;

import java.util.Set;

public interface TokenServicePort {
    String generateToken(String email, Set<String> roles, Set<String> permissions);

    String extractUsername(String token);

    boolean isTokenValid(String token);
}

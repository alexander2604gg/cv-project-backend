package com.alexander.cv_project.auth.infrastructure.adapter.in.web.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.alexander.cv_project.auth.domain.port.out.TokenServicePort;
import com.alexander.cv_project.auth.domain.port.out.UserRepositoryPort;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenServicePort tokenServicePort;
    private final UserRepositoryPort userRepositoryPort;

    public JwtAuthenticationFilter(TokenServicePort tokenServicePort, UserRepositoryPort userRepositoryPort) {
        this.tokenServicePort = tokenServicePort;
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String token = extractBearerToken(request);

        if (token != null && tokenServicePort.isTokenValid(token)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            String email = tokenServicePort.extractUsername(token);

            List<GrantedAuthority> authorities = userRepositoryPort.findByEmail(email)
                    .map(user -> user.getRoles().stream()
                            .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role.getCode()))
                            .toList())
                    .orElse(Collections.emptyList());

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    email,
                    null,
                    authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String extractBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}

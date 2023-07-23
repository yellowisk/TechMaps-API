package br.ifsp.techmaps.configs.auth.jwt;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;
    private final JwtTokenHelper jwtTokenHelper;

    public JwtTokenVerifier(JwtProperties jwtProperties, JwtTokenHelper jwtTokenHelper) {
        this.jwtProperties = jwtProperties;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(isFromPermittedPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader(jwtProperties.getAuthorizationHeader());
        if (jwtTokenHelper.hasInvalidAuthorization(authorizationHeader)) {
            final String error = "Authorization header is missing or invalid.";
            log.error("Token verification error: {}", error);
            response.addHeader(jwtProperties.getAuthorizationHeader(), error);
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authorizationHeader.replace(jwtProperties.getTokenPrefix(), "");
        try {
            final String principal = jwtTokenHelper.extractClaims(token).getSubject();
            final UUID userId = UUID.fromString(principal);
            final var authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error("Token verification error: {}", e.getMessage());
            jwtTokenHelper.configureTokenErrorResponse(response, e.getMessage());
        }
    }

    private boolean isFromPermittedPath(HttpServletRequest request) {
        return request.getServletPath().equals("/register")
                || request.getServletPath().equals("/login")
                || request.getServletPath().equals("/refresh-token");
    }
}

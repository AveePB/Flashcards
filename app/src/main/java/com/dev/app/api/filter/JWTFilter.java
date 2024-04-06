package com.dev.app.api.filter;

import com.dev.app.api.config.SecurityConfig;
import com.dev.app.bll.manager.JWTManager;
import com.dev.app.bll.manager.UserManager;
import com.dev.app.util.token.JsonWebToken;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String PREFIX = "Bearer ";

    private final UserManager userManager;
    private final JWTManager jwtManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(PREFIX)) {
            String bearer = authHeader.substring(PREFIX.length());
            JsonWebToken jwt = new JsonWebToken(bearer);

            Optional<String> username = jwtManager.extractUsername(jwt, SecurityConfig.SECRET_KEY_256_BIT);

            if (username.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userManager.loadUserByUsername(username.get());
                boolean isTokenValid = jwtManager.validate(jwt, SecurityConfig.SECRET_KEY_256_BIT, userDetails.getUsername());

                if (isTokenValid) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}

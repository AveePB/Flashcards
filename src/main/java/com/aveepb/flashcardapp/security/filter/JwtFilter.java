package com.aveepb.flashcardapp.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private static final String AUTH_HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String headerContent = request.getHeader(AUTH_HEADER);

        //Proceed if there is a token.
        if (headerContent != null && headerContent.startsWith(PREFIX)) {
            String token = headerContent.substring(PREFIX.length());

            //...

        }

        //Run next filter.
        filterChain.doFilter(request, response);
    }
}

package com.financeApp.AmortizedAnalysis.filter;

import com.financeApp.AmortizedAnalysis.service.JWTService;
import com.financeApp.AmortizedAnalysis.service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        // skip CORS preflight
        return "OPTIONS".equalsIgnoreCase(request.getMethod());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        // Public endpoints
        if (path.startsWith("/api/users/signup") || path.startsWith("/api/users/login") || path.startsWith("/api/users/all")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // No token → let Spring Security handle (will be 401 on protected routes)
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);
        String username = null;
        try {
            username = jwtService.extractUsername(token);
        } catch (Exception e) {
            // Bad token → let Security handle
            chain.doFilter(request, response);
            return;
        }

        var context = SecurityContextHolder.getContext();
        var currentAuth = context.getAuthentication();

        if (username != null && (currentAuth == null || !currentAuth.isAuthenticated()
                || currentAuth instanceof org.springframework.security.authentication.AnonymousAuthenticationToken)) {

            var userDetails = this.context.getBean(com.financeApp.AmortizedAnalysis.service.MyUserDetailsService.class)
                    .loadUserByUsername(username);

            if (jwtService.validateToken(token, userDetails)) {
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }
}
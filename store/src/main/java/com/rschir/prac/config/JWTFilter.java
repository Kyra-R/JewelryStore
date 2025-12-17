package com.rschir.prac.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.rschir.prac.security.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
@Order(1) //Ordered.HIGHEST_PRECEDENCE - error, does before security filters
/*
2025-12-12T20:59:44.086+03:00 DEBUG 6580 --- [nio-8080-exec-2] o.s.security.web.FilterChainProxy : Securing GET /jewelry/admin/get
2025-12-12T20:59:44.089+03:00 DEBUG 6580 --- [nio-8080-exec-2] o.s.s.w.a.AnonymousAuthenticationFilter : Set SecurityContextHolder to anonymous SecurityContext
2025-12-12T20:59:44.092+03:00 DEBUG 6580 --- [nio-8080-exec-2] o.s.s.w.a.Http403ForbiddenEntryPoint : Pre-authenticated entry point called. Rejecting access
2025-12-12T20:59:44.105+03:00 DEBUG 6580 --- [nio-8080-exec-2] o.s.security.web.FilterChainProxy : Securing GET /error
2025-12-12T20:59:44.108+03:00 DEBUG 6580 --- [nio-8080-exec-2] o.s.s.w.a.AnonymousAuthenticationFilter : Set SecurityContextHolder to anonymous SecurityContext
2025-12-12T20:59:44.109+03:00 DEBUG 6580 --- [nio-8080-exec-2] o.s.s.w.a.Http403ForbiddenEntryPoint : Pre-authenticated entry point called. Rejecting access
 */
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Autowired
    public JWTFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("JWTFilter triggered for: " + request.getRequestURI());

        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println("Auth in process...");
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            try {
                Authentication authentication = jwtUtil.validateTokenAndRetrieveAuth(jwt);
                //System.out.println(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println("Authorities: " + authentication.getAuthorities());
                System.out.println("Auth: " + SecurityContextHolder.getContext().getAuthentication());
                System.out.println("JWT correct");
            } catch (JWTVerificationException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT Token");
                System.out.println("Oops, JWT Token wrong");
                return;
            }
        }

        System.out.println("Next filter!");
        filterChain.doFilter(request, response);
    }
}
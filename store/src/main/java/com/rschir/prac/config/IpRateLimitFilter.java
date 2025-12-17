package com.rschir.prac.config;

import com.rschir.prac.services.llm.PromptLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
public class IpRateLimitFilter extends OncePerRequestFilter {
    private PromptLimitService promptLimitService;

    @Autowired
    IpRateLimitFilter(PromptLimitService promptLimitService){
        this.promptLimitService = promptLimitService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ip = request.getRemoteAddr();

        System.out.println("IpRateFilter triggered for: " + ip + " " + request.getRequestURI());

        if (!promptLimitService.allowRequest(ip)) {
            response.setStatus(429);
            response.getWriter().write("Too many requests!");
            return;
        }

        filterChain.doFilter(request, response);
    }
}

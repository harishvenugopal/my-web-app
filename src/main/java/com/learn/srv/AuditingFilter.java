package com.learn.srv;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class AuditingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        ServletContext context = request.getServletContext();
        
        // Safely increment a global traffic counter shared across the entire app footprint
        synchronized (context) {
            Integer totalHits = (Integer) context.getAttribute("globalHitCounter");
            if (totalHits == null) {
                totalHits = 0;
            }
            totalHits++;
            context.setAttribute("globalHitCounter", totalHits);
        }

        System.out.println("====== [AUDIT FILTER] Global Request Intercepted. Hit Counter incremented. ======");
        
        // Pass execution down the pipeline
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
